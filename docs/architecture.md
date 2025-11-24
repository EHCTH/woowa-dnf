## 아키텍처 & 설계

### 구현 목적
- 프리코스 1~3주차 동안 헥사고날 아키텍처를 연습해 왔고,  
  이번 미션에서도 같은 아키텍처 스타일을 적용해 설계·구현했습니다.


### 설계 방향
- **DDD** 스타일로 캐릭터, 장비, 아바타, 버프, 크리쳐 등을 도메인 객체로 분리했습니다.
- 외부 API(네오플), DB(MySQL), WebSocket 등 I/O 의존성을 **포트/어댑터 구조**로 감싸  
  도메인 계층이 인프라스트럭처에 직접 의존하지 않도록 설계했습니다.
- 조회/갱신 흐름은 한 곳에서 관리할 수 있도록  
  **저장용 퍼사드(`CharacterRefreshFacade`)** 와 **검색용 퍼사드(`CharacterSearchFacade`)** 를 두어 오케스트레이션합니다.

### 레이어 구성
- **domain**
    - 캐릭터(`Character`)를 중심으로, 장비, 아바타, 버프, 크리쳐, 플래그, 채팅 등 도메인 모델이 위치합니다.

- **application**
    - 유스케이스 인터페이스  
      (`GetCharacterDetailUseCase`, `SaveCharacterUseCase`,  
      `ClearRoomChatUseCase`, `SendChatMessageUseCase`, `GetChatMessageUseCase` 등)가 위치합니다.
    - 이를 구현하는 퍼사드/서비스  
      (`CharacterRefreshFacade`, `CharacterParallelLoader`,  
      `ClearRoomChatService`, `ChatMessageService`, `GetChatMessageService`)가  
      도메인 객체를 조합하고 외부 시스템 호출을 오케스트레이션합니다.

- **interfaces**
    - **입력 어댑터**가 위치합니다.
    - 이 레이어는 주로 `application.port.inbound` 에 정의된 유스케이스에 의존하여 요청을 위임합니다.

- **infrastructure**
    - 외부 시스템 및 DB와 **실제로 통신하는 구현체**들이 모여 있는 계층입니다.
    - `external`
        - 네오플 오픈 API를 호출하는 어댑터들이 위치합니다.
        - `application.port.outbound` 를 구현해서 외부 API 호출과 External DTO 매핑을 담당합니다.
    - `persistence`
        - `CharacterSnapshotEntity`, `CharacterSnapshotId` 같은 JPA 엔티티와  
          도메인 ↔ 스냅샷 매핑을 수행하는 `CharacterSnapshotMapper` 가 위치합니다.
        - 도메인 상태를 스냅샷 형태로 DB에 영속화하기 위해 이렇게 분리했습니다.
    - `repository`
        - `JpaCharacterAdapter`, `MemoryCharacterAdapter` 등 실제 저장소 접근 구현체가 위치합니다.
        - `application.port.outbound` 의 리포지토리 포트를 구현하여  
          도메인/애플리케이션 레이어는 저장 방식(MySQL / 메모리)을 알 필요 없이 동일한 인터페이스만 사용하도록 합니다.

- **common**
    - 애플리케이션 전역에서 재사용되는 공통 기능 모듈입니다.
    - `aop키
        - `@CharacterLog`, `CharacterLogAspect` 를 통해 캐릭터 조회/저장 유즈케이스의 **입·출력과 실행 시간 로깅**을 담당합니다.
    - `config`
        - `ConcurrencyConfig`: Java 21 **버추얼 스레드 기반** `ExecutorService`를 설정합니다.
        - `HttpConfig`: 네오플 오픈 API 호출용 기본 baseUrl과 HTTP 연결/읽기 타임아웃을 관리합니다.
        - `NeopleProps`: 네오플 오픈 API의 **baseUrl, API ** 등을 설정합니다
    - `exception`
        - `DomainException`: API -> 도메인 변환 예외 클래스입니다
        - `ApiExceptionHandler`: 컨트롤러 단의 전역 예외 처리기입니다
        - `BoundedAsync`: 여러 개의 작업을 비동기로 실행하되, 동시에 실행 가능한 개수를 `maxConcurrency`로 제한하는 유틸리티입니다.


### 프로필에 따른 저장소 분리
- EC2 호스트에서 `docker compose` 실행 시 `SPRING_PROFILES_ACTIVE` 값을 바꿔서
  **동일한 애플리케이션 코드를 메모리/DB 환경에 맞게 실행**할 수 있도록 구성했습니다.
    - 로컬 기본값: `memory`
    - EC2 + Docker Compose: `SPRING_PROFILES_ACTIVE=mysql`
- **`memory` 프로필**
    - `Map` 기반의 메모리 저장소를 사용합니다.
    - `@Profile("memory")` 가 붙은 `MemoryCharacterAdapter`, `MemoryChatMessageAdapter` 가 활성화되며,  
      외부 DB 없이 빠르게 개발/테스트할 수 있습니다.

- **`mysql` 프로필**
    - MySQL + JPA를 이용해 캐릭터 스냅샷 및 채팅 메시지를 영속화합니다.
    - `@Profile("mysql")` 이 붙은 `JpaCharacterAdapter`, `JpaChatMessageAdapter` 가 활성화되며,  
      애플리케이션 코드는 여전히 포트 인터페이스만 의존합니다.

### 동작 방식
외부 API와 DB를 조합해서 캐릭터 정보 및 채팅을 다루는 흐름은 다음과 같습니다.

```
[캐릭터]

[저장]
외부 API(JSON)
  -> External DTO
  -> DOMAIN(캐릭터)
  -> SNAPSHOT(저장용 엔티티)
  -> 저장(MySQL / 메모리)

[조회]
조회(MySQL / 메모리)
  -> SNAPSHOT(저장용 엔티티)
  -> DOMAIN(캐릭터)
  -> View DTO(화면용)


[채팅]

[저장]
Client DTO(요청)
  -> Domain(ChatMessage)
  -> 저장(MySQL / 메모리)

[조회]
조회(MySQL / 메모리)
  -> Domain(ChatMessage)
  -> View DTO(응답)
```
