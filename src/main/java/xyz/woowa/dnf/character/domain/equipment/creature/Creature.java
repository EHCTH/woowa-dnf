package xyz.woowa.dnf.character.domain.equipment.creature;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.Artifacts;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.CreatureProfile;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.Detail;

@Getter
@Builder
public class Creature {
    private final CreatureProfile base;
    private final ItemName clone;
    private final Artifacts artifacts;
    private final Detail detail;
}
