package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import xyz.woowa.dnf.character.domain.equipment.common.ItemName;

public record SetItemInfo(ItemName itemName,
                          RaritySetPoint raritySetPoint,
                          Integer setPoint,
                          Description description) {
    public static SetItemInfo EMPTY =
            new SetItemInfo(
                    ItemName.EMPTY,
                    RaritySetPoint.NONE,
                    0,
                    Description.EMPTY);
}
