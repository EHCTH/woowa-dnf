package xyz.woowa.dnf.character.application.query.dto.equipment.creature;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.ItemDetailDto;

import java.util.List;

@Builder
@Getter
@ToString
public class CreatureDto {
    private final ProfileDto base;
    private final ItemValue clone;
    private final ItemDetailDto detail;
    private final List<ArtifactDto> artifacts;
    private final CreatureSkillDto creatureSkill;


    public boolean hasSkill() {
        return creatureSkill.hasSkill();
    }
    public boolean hasOverSkill() {
        return creatureSkill.hasOverSkill();
    }
}
