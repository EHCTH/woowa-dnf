package xyz.woowa.dnf.character.application.query.dto.common;

public record ItemValue(String id, String name) {
    public static ItemValue EMPTY = new ItemValue("", "");
    public boolean hasId() {
        return !id.isEmpty();
    }
    public boolean hasName() {
        return !name.isEmpty();
    }
}
