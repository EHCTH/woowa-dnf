package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;

@Getter
@Builder
public class SetItemInfoDto {
    public static final SetItemInfoDto EMPTY = new SetItemInfoDto(ItemValue.EMPTY, "", "", 0, "", "");
    private final ItemValue baseItem;
    private final String rarity;
    private final String rarityDisplay;
    private final Integer setPoint;
    private final String itemExplainHtml;
    private final String buffExplainHtml;

    public boolean hasName() {
        return baseItem.hasName();
    }

    public boolean hasId() {
        return baseItem.hasId();
    }

    public boolean hasExplain() {
        return !itemExplainHtml.isEmpty();
    }

    public String getName() {
        return baseItem.name();
    }

    public String getId() {
        return baseItem.id();
    }
}
