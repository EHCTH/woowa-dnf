package xyz.woowa.dnf.character.application.query.assembler.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalBasePort;
import xyz.woowa.dnf.character.domain.base.*;

import static org.assertj.core.api.Assertions.assertThat;

class BaseMapperTest {

    private final CommonValueMapper commonValueMapper = new CommonValueMapper();
    private final BaseMapper baseMapper = new BaseMapper(commonValueMapper);

    @Test
    @DisplayName("도메인 변환 테스트")
    void 도메인_변환_테스트() {
        // give
        ExternalBasePort.BasicDto basicDto = createBasicDto();

        // when
        Base base = baseMapper.toDomain(basicDto);

        // then
        assertThat(base.profile()).isEqualTo(new Profile(
                "caecbef3c8cb5c58be8bba2f5fedd167",
                Server.CAIN, new Name("JAVA"),
                115, "眞 헌터", 78219));

        assertThat(base.social()).isEqualTo(new Social("ORACLE", "아람"));

    }

    private ExternalBasePort.BasicDto createBasicDto() {

        return new ExternalBasePort.BasicDto(
                "cain",
                "caecbef3c8cb5c58be8bba2f5fedd167",
                "JAVA",
                115,
                "眞 헌터",
                78219,
                "ORACLE",
                "아람"
        );
    }
}
