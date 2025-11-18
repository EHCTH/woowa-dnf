package xyz.woowa.dnf.character.application.query.dto.equipment.avatar;

import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;

public record ProfileDto(String slot, String rarity, ItemValue itemName, String fame) {
    public boolean hasId() {
        return itemName.hasId();
    }
    public String name() {
        return itemName.name();
    }
    public String id() {
        return itemName.id();
    }
}
