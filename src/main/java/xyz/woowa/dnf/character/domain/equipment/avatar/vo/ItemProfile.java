package xyz.woowa.dnf.character.domain.equipment.avatar.vo;

import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;

public record ItemProfile(AvatarSlot avatarSlot, ItemName base, Rarity rarity) {
    public String slotName() {
        return avatarSlot.getDisplayName();
    }
    public String id() {
        return base.id();
    }
    public String name() {
        return base.name();
    }
    public String getRarity() {
        return rarity.name();
    }

}
