package xyz.woowa.dnf.character.domain.equipment.common;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Rarity {
    태초, 에픽, 레전더리, 유니크, 레어, 언커먼, 커먼, 신화, 크로니클, 언노운;
    public static Rarity findByValue(String value) {
        if (value == null) {
            return 언노운;
        }
        return Arrays.stream(values())
                .filter(x -> value.contains(x.name()))
                .findFirst()
                .orElse(언노운);
    }

}
