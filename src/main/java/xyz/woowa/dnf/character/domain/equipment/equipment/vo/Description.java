package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

public record Description(String explain, String buffExplain) {
    public static final Description EMPTY = new Description("", "");
}
