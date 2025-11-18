package xyz.woowa.dnf.character.application.query.assembler.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.dto.base.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.base.ServerDto;
import xyz.woowa.dnf.character.application.query.dto.base.SocialDto;
import xyz.woowa.dnf.character.domain.base.*;

import static org.assertj.core.api.Assertions.assertThat;

class BaseDtoMapperTest {
    private final BaseDtoMapper baseDtoMapper = new BaseDtoMapper();

    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {
        // give
        Base base = createBase();

        // when
        BaseDto dto = baseDtoMapper.toDto(base);

        // then
        ProfileDto profileDto = dto.profileDto();
        assertThat(profileDto.characterId()).isEqualTo("caecbef3c8cb5c58be8bba2f5fedd167");
        assertThat(profileDto.characterName()).isEqualTo("JAVA");
        assertThat(profileDto.level()).isEqualTo(115);
        assertThat(profileDto.jobGrowName()).isEqualTo("眞 헌터");
        assertThat(profileDto.fame()).isEqualTo(78219);
        assertThat(profileDto.image()).isEqualTo("https://img-api.neople.co.kr/df/servers/cain/characters/caecbef3c8cb5c58be8bba2f5fedd167?zoom=1");

        ServerDto serverDto = dto.serverDto();
        assertThat(serverDto.serverId()).isEqualTo("cain");
        assertThat(serverDto.korean()).isEqualTo("카인");

        SocialDto socialDto = dto.socialDto();
        assertThat(socialDto.adventureName()).isEqualTo("ORACLE");
        assertThat(socialDto.guildName()).isEqualTo("아람");
    }


    private Base createBase() {
        Profile profile = new Profile("caecbef3c8cb5c58be8bba2f5fedd167", Server.CAIN, new Name("JAVA"), 115, "眞 헌터", 78219);
        Social social = new Social("ORACLE", "아람");
        return Base.builder()
                .profile(profile)
                .social(social)
                .build();
    }

}
