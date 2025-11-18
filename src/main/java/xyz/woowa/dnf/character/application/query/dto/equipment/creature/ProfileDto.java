package xyz.woowa.dnf.character.application.query.dto.equipment.creature;

import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;

public record ProfileDto(ItemValue itemName, String rarity, String fame) {
    public boolean hasId() {
        return itemName.hasId();
    }
}
