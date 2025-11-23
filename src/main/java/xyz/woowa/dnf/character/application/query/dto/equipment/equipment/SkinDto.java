package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;

public record SkinDto(String slot, String id, String name, String rarity) {
    public boolean hasId() {
        return !id.isEmpty();
    }
    public String displayName() {
        return "[스킨] " + name;
    }
}
