package xyz.woowa.dnf.character.application.query.assembler.common;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.EmblemSlot;

@Component
public class EmblemColorMapper {
    public String colorMapper(String color, String name) {
        EmblemSlot byName = EmblemSlot.findByName(name);
        if (byName == EmblemSlot.EMPTY) {
            return color;
        }
        return byName.getColor();
    }
}
