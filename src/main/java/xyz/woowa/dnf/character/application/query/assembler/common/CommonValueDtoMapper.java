package xyz.woowa.dnf.character.application.query.assembler.common;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.List;

@Component
public class CommonValueDtoMapper {
    public ItemValue toItemValue(String id, String name) {
        if (id == null || id.isEmpty()) {
            return ItemValue.EMPTY;
        }
        return new ItemValue(id, name);
    }
    public NameValue toNameValue(Status status) {
        return toNameValue(status.name(), status.value());
    }
    public NameValue toNameValue(String name, String value) {
        if (name == null) {
            return NameValue.EMPTY;
        }
        return new NameValue(name, value);
    }
}
