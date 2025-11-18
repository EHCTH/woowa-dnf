package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

public record UpgradeInfo(ItemName itemName, Rarity rarity, FusionOption fusionOption) {
    public boolean hasId() {
        return itemName != null;
    }

    public String id() {
        if (hasId()) {
            return itemName.id();
        }
        return "";

    }
    public String name() {
        if (hasId()) {
            return itemName.name();
        }
        return "";
    }

    public String buff() {
        return fusionOption().buff();
    }
    public String explain() {
        return fusionOption.itemExplain();
    }

    public String buffExplain() {
        return fusionOption.buffExplain();
    }
}
