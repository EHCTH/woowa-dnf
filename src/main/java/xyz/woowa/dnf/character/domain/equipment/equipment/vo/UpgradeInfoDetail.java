package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.Builder;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

@Builder
public record UpgradeInfoDetail(
        String buffExplain,
        String itemExplain,
        String fame,
        Rarity rarity,
        String itemFlavorText) {
    public static UpgradeInfoDetail EMPTY = new UpgradeInfoDetail("", "", "", Rarity.언노운, "");
}
