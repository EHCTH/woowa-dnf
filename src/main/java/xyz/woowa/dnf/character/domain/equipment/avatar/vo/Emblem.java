package xyz.woowa.dnf.character.domain.equipment.avatar.vo;

import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

public record Emblem(EmblemSlot emblemSlot, ItemName base, Rarity rarity) {
    public String emblemColor() {
        return emblemSlot.getColor();
    }
}
