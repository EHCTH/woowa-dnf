package xyz.woowa.dnf.character.application.query.port.inbound;

import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;

public interface GetBuffEquipmentUseCase {
    BuffEquipment buffEquipment(GetCharacterDetailUseCase.Command command);

}
