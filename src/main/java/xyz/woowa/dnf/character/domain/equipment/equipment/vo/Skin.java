package xyz.woowa.dnf.character.domain.equipment.equipment.vo;


import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

public record Skin(Slot slot, ItemName base, Rarity rarity) {
    public static Skin EMPTY = new Skin(Slot.UNKNOWN, ItemName.EMPTY, Rarity.언노운);
}
