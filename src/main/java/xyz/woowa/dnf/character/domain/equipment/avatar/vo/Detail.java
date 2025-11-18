package xyz.woowa.dnf.character.domain.equipment.avatar.vo;

import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.Collections;
import java.util.List;

public record Detail(String fame, String itemExplain, List<Status> status) {
    public static Detail EMPTY = new Detail("", "", Collections.emptyList());
    public static Detail of(String fame, List<Status> status) {
        return new Detail(fame, "", status);
    }
}
