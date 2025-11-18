package xyz.woowa.dnf.character.domain.equipment.buff.equipment;

import java.util.Collections;
import java.util.List;

public record SkillInfo(String name, String level, String dsec, List<String> values) {
    public static SkillInfo EMPTY = new SkillInfo("", "", "", Collections.emptyList());
}
