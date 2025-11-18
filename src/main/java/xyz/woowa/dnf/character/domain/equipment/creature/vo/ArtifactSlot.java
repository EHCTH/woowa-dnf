package xyz.woowa.dnf.character.domain.equipment.creature.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ArtifactSlot {
    RED("RED"),
    BLUE("BLUE"),
    GREEN("GREEN"),
    EMPTY("없음");
    private final String color;

    public static ArtifactSlot findByColor(String color) {
        return Arrays.stream(values())
                .filter(artifactSlot -> artifactSlot.color.equals(color))
                .findAny()
                .orElse(EMPTY);
    }
}
