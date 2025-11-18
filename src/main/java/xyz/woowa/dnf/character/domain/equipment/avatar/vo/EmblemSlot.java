package xyz.woowa.dnf.character.domain.equipment.avatar.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum EmblemSlot {
    PLATINUM("플래티넘"),
    RED("붉은빛"),
    YELLOW("노란빛"),
    BLUE("푸른빛"),
    GREEN("녹색빛"),
    MULTI("다색"),
    EMPTY("없음");
    private final String color;

    public static EmblemSlot findByColor(String color) {
        return Arrays.stream(values())
                .filter(emblemSlot -> emblemSlot.color.equals(color))
                .findAny()
                .orElse(EMPTY);
    }
    public static EmblemSlot findByName(String name) {
        return Arrays.stream(values())
                .filter(x -> name.contains(x.color))
                .findAny()
                .orElse(EMPTY);
    }

}
