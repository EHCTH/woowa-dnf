package xyz.woowa.dnf.character.application.query.port.inbound;

import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

import java.util.List;

public interface GetCharacterDetailUseCase {
    record Command(String serverId, String id) {
    }

    List<Base> getAll(List<Command> commands);

    Base get(Command command);
    Equipment equipment(Command command);
    BuffEquipment buffEquipment(Command command);

    Avatars avatars(Command command);
    Avatars buffAvatars(Command command);

    Creature creature(Command command);
    Creature buffCreature(Command command);

    Flag flag(Command command);
}
