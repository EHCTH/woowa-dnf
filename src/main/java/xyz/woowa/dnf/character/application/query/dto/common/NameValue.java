package xyz.woowa.dnf.character.application.query.dto.common;

public record NameValue(String name, String value) {
    public static NameValue EMPTY = new NameValue("", "");

    public boolean hasName() {
        return !name.isEmpty();
    }

    public boolean hasValue() {
        return !value.isEmpty();
    }

}
