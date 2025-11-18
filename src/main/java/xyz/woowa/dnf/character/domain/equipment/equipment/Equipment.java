package xyz.woowa.dnf.character.domain.equipment.equipment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Equipment {
    private final SetItemInfo setItemInfo;
    private final List<EquipmentItem> equipmentItems;
}
