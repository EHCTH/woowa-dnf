package xyz.woowa.dnf.character.domain.equipment.buff.equipment;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;


@Builder
@Getter
public class BuffEquipItem {
    private final ItemProfile base;
    private final Detail detail;
    private final Status skills;

}
