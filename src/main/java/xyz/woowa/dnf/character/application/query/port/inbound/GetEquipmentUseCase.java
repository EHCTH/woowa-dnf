package xyz.woowa.dnf.character.application.query.port.inbound;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;


public interface GetEquipmentUseCase {
    Equipment equipment(GetCharacterDetailUseCase.Command command);
}
