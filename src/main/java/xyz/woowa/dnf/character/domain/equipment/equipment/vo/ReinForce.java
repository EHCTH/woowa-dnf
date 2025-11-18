package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

public record ReinForce(String name, String value) {
    public static ReinForce enforce(String value) {
        return new ReinForce("강화", value);
    }
}
