package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.Builder;
import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.Collections;
import java.util.List;

@Builder
public record ItemDetail(
        String buffExplain,
        String itemExplain,
        String itemFlavorText,
        String fame,
        List<Status> itemStatus,
        Tune tune) {

    public static ItemDetail EMPTY = new ItemDetail("", "",
            "", "", Collections.emptyList(), Tune.EMPTY);

}

