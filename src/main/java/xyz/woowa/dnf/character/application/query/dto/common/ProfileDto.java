package xyz.woowa.dnf.character.application.query.dto.common;

public record ProfileDto(String slot, String rarity, ItemValue itemName, ItemValue detailName, String fame) {
    public boolean hasId() {
        return itemName.hasId();
    }

}
