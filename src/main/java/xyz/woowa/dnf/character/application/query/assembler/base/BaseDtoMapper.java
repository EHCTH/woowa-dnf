package xyz.woowa.dnf.character.application.query.assembler.base;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.dto.base.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.base.ServerDto;
import xyz.woowa.dnf.character.application.query.dto.base.SocialDto;
import xyz.woowa.dnf.character.domain.base.Base;

@Component
public class BaseDtoMapper {
    public BaseDto toDto(Base base) {
        var serverDto = new ServerDto(
                base.serverId(),
                base.serverName()
        );

        var profileDto = new ProfileDto(
                base.characterId(),
                base.characterName(),
                base.level(),
                base.jobGrowName(),
                base.fame(),
                imageUrlOf(base.serverId(), base.characterId())
        );

        var socialDto = new SocialDto(
                base.adventureName(),
                base.guildName()
        );

        return new BaseDto(profileDto, serverDto, socialDto);
    }
    private static String imageUrlOf(String serverId, String characterId) {
        return "https://img-api.neople.co.kr/df/servers/%s/characters/%s?zoom=1".formatted(serverId, characterId);
    }
}
