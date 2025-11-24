## 초기 세팅

### 1. Neople API Key 설정

네오플 오픈 API 키가 필요합니다.  
`https://developers.neople.co.kr/`  
위 링크로 접속 하여 회원 가입 후 API 키를 받아서 `src/main/resources/application.properties` 에 다음과 같이 설정합니다.

```
external.neople.api-key=${NEOPLE_API_KEY:PUT_YOUR_NEOPLE_KEY_HERE}
```
