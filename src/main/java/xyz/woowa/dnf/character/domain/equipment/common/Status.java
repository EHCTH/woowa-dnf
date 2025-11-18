package xyz.woowa.dnf.character.domain.equipment.common;

public record Status(String name, String value) {
    public static Status EMPTY = new Status("", "");
}
