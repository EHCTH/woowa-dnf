package xyz.woowa.dnf.character.domain.equipment.buff.equipment;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BuffEquipment {
    private final SkillInfo skillInfo;
    private final List<BuffEquipItem> buffEquipItems;
}
