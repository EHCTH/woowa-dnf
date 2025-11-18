package xyz.woowa.dnf.character.domain.equipment.avatar;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Emblem;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Emblems;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;

import java.util.List;

@Builder
@Getter
public class Avatar {
    private final ItemProfile base;
    private final ItemName clone;
    private final Emblems emblems;
    private final String optionAbility;
    private final Detail detail;
    public List<Emblem> emblems() {
        return emblems.getEmblems();
    }
}
