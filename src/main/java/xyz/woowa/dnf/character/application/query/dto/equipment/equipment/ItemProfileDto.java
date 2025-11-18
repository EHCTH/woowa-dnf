package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;

import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;

public record ItemProfileDto(String slot, String rarity, ItemValue itemName, ItemValue itemType) {
    public boolean hasId() {
        return itemName().hasId();
    }
    public String name() {
        return itemName.name();
    }
    public String id() {
        return itemName.id();
    }
    public String typeName() {
        return itemType.name();
    }
    public String typeId() {
        return itemType.id();
    }

}
