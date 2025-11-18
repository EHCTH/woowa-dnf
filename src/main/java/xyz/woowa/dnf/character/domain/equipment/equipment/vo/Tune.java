package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.Collections;
import java.util.List;

public record Tune(Integer level, Integer setPoint, List<Status> status, String name) {
    public static Tune EMPTY = new Tune(0, 0, Collections.emptyList(), "");
}
