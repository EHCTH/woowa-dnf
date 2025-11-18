package xyz.woowa.dnf.character.application.query.assembler.common;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;


@Component
public class CommonValueMapper {
    public ItemName toItemName(String id, String name) {
        if (id == null || id.isEmpty()) {
            return ItemName.EMPTY;
        }
        return new ItemName(id, name);
    }
    public Status toNameStatus(String name, String value) {
        if (name == null) {
            return Status.EMPTY;
        }
        return new Status(name, value);
    }
}
