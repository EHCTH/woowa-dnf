package xyz.woowa.dnf.character.application.query.dto.base;


public record BaseDto(ProfileDto profileDto, ServerDto serverDto, SocialDto socialDto) {

    public String characterId() {
        return profileDto().characterId();
    }
    public String characterName() {
        return profileDto.characterName();
    }
    public int level() {
        return profileDto.level();
    }
    public String jobGrowName() {
        return profileDto.jobGrowName();
    }
    public int fame() {
        return profileDto.fame();
    }
    public String image() {
        return profileDto.image();
    }

    public String serverId() {
        return serverDto.serverId();
    }
    public String serverName() {
        return serverDto.korean();
    }

    public String adventureName() {
        return socialDto().adventureName();
    }
    public String guildName() {
        return socialDto.guildName();
    }

    public int descByFame(BaseDto other) {
        return Integer.compare(other.fame(), this.fame());
    }
}
