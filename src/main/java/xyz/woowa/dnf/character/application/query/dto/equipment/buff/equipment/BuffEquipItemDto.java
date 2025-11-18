package xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.ProfileDto;

@Getter
@Builder
public class BuffEquipItemDto {
    private final ProfileDto base;
    private final ItemValue detail;
    private final NameValue skill;
    private final String baseStatusHtml;
    private final String detailStatusHtml;
    private final String otherStatusHtml;
    private final String itemExplainHtml;


}
