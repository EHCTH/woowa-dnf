package xyz.woowa.dnf.character.application.query.dto.equipment.avatar;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.ItemDetailDto;

import java.util.List;

@Builder
@Getter
@ToString
public class AvatarDto {
    private final ProfileDto base;
    private final ItemValue clone;
    private final String optionAbility;
    private final List<EmblemDto> emblems;
    private final ItemDetailDto detail;
    public boolean hasOptionAbility() {
        return !optionAbility.isEmpty();
    }
    public boolean equalsSlotName(String slot) {
        return base.slot().equals(slot);
    }
    public boolean isAura() {
        return base.slot().equals("오라 아바타");
    }
}
