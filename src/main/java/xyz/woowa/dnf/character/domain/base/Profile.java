package xyz.woowa.dnf.character.domain.base;

public record Profile(String characterId, Server server, Name name, int level, String jobGrowName, int fame) {
    public String getName() {
        return name.value();
    }

}
