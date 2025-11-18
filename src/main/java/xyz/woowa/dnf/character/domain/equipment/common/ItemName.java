package xyz.woowa.dnf.character.domain.equipment.common;

public record ItemName(String id, String name) {
    public static ItemName EMPTY = new ItemName("", "");

}
