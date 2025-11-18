package xyz.woowa.dnf.character.domain.equipment.flag;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ReinForce;
import xyz.woowa.dnf.character.domain.equipment.flag.vo.Gem;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
public class Flag {
    public static Flag EMPTY = new Flag(
            ItemProfile.EMPTY,
            Detail.EMPTY,
            ReinForce.enforce("0"),
            Collections.emptyList());
    private final ItemProfile base;
    private final Detail detail;
    private final ReinForce reinForce;
    private final List<Gem> gems;

}
