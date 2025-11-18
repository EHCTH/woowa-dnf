package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class EquipmentDto {
    private final SetItemInfoDto setItemInfo;
    private final List<EquipmentItemDto> equipmentItems;

}
