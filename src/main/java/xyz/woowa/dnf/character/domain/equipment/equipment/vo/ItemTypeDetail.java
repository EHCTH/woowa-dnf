package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import xyz.woowa.dnf.character.domain.equipment.common.ItemName;

public record ItemTypeDetail(ItemName itemName) {
    public static ItemTypeDetail EMPTY = new ItemTypeDetail(ItemName.EMPTY);
    public String id() {
        return itemName.id();
    }
    public String name() {
        return itemName.name();
    }
}
