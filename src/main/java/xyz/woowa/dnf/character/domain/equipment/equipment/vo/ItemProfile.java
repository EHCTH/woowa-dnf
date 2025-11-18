package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.Builder;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

@Builder
public record ItemProfile(Slot slot, Rarity rarity, ItemName itemName, ItemName itemTypeDetail) {
    public static ItemProfile EMPTY = new ItemProfile(Slot.UNKNOWN, Rarity.언노운, ItemName.EMPTY, ItemName.EMPTY);
    public String getRarity() {
        return rarity.name();
    }
    public String getId() {
        return itemName.id();
    }
    public String getName() {
        return itemName.name();
    }
    public String getDetailId() {
        return itemTypeDetail.id();
    }
    public String getDetailName() {
        return itemTypeDetail.name();
    }
}
