package xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BuffEquipmentDto {
    private final SkillInfoDto skillInfo;
    private final List<BuffEquipItemDto> buffEquipItems;

    public boolean hasSkillInfo() {
        return !skillInfo.name().isEmpty();
    }

}
