package xyz.woowa.dnf.character.application.query.dto.common;

import lombok.Getter;

@Getter
public class ReinforceDto {
    public static ReinforceDto EMPTY = new ReinforceDto("강화", "0");
    private static final String DIMENSION_PREFIX = "차원의";

    private final String name;
    private final String value;

    private ReinforceDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static ReinforceDto enforce(String value) {
        return new ReinforceDto("강화", value);
    }

    public String reinforceClassType() {
        if (name.equals("강화")) {
            return "norm";
        }
        return "amp";
    }
    public static ReinforceDto dimension(String name, String value) {
        return new ReinforceDto(name, value);
    }

    public String getName() {
        return name.replace(DIMENSION_PREFIX, "")
                .trim();
    }
    public boolean hasUpgrade() {
        return !value.equals("0");
    }

    public static void main(String[] args) {
    }
}
