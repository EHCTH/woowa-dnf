package xyz.woowa.dnf.character.domain.base;

import lombok.Builder;


@Builder
public record Base(Profile profile, Social social, BaseStatus baseStatus) {

    public String serverId() {
        return profile.server().getEnglish();
    }

    public String serverName() {
        return profile.server().getKorean();
    }

    public String characterId() {
        return profile.characterId();
    }

    public String characterName() {
        return profile.getName();
    }

    public int level() {
        return profile.level();
    }

    public String jobGrowName() {
        return profile.jobGrowName();
    }

    public int fame() {
        return profile.fame();
    }

    public String adventureName() {
        return social.adventureName();
    }

    public String guildName() {
        return social.guildName();
    }
}
