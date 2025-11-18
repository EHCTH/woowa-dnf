package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;

import xyz.woowa.dnf.character.application.query.dto.common.NameValue;

import java.util.List;

public record TuneDto(Integer level, String name, List<NameValue> status) {
    private static final String TUNE_UNIT = "I";
    public boolean hasName() {
        return !name.isEmpty();
    }
    public boolean hasLevel() {
        return level != 0;
    }

    public String display() {
        if (level != 0) {
            return "[" + TUNE_UNIT.repeat(level) + "]";
        }
        return "";
    }
}
