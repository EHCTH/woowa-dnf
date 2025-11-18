package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

public record FusionOption(String buff, String itemExplain, String buffExplain) {
    public static FusionOption EMPTY = new FusionOption("", "",  " ");
}
