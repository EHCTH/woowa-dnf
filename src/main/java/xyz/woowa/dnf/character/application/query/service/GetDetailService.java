package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.port.inbound.*;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDetailService implements GetCharacterDetailUseCase {
    private final GetBaseUseCase getBaseUseCase;
    private final GetEquipmentUseCase getEquipmentUseCase;
    private final GetAvatarUseCase getAvatarUseCase;
    private final GetCreatureUseCase creatureUseCase;
    private final GetBuffEquipmentUseCase buffEquipmentUseCase;
    private final GetFlagUseCase getFlagUseCase;

    @Override
    public List<Base> getAll(List<Command> commands) {
        return getBaseUseCase.getAll(commands);
    }

    @Override
    public Base get(Command command) {
        return getBaseUseCase.get(command);
    }

    @Override
    public Equipment equipment(Command command) {
        return getEquipmentUseCase.equipment(command);
    }

    @Override
    public Avatars avatars(Command command) {
        return getAvatarUseCase.avatars(command);
    }

    @Override
    public Avatars buffAvatars(Command command) {
        return getAvatarUseCase.buffAvatars(command);
    }

    @Override
    public Creature creature(Command command) {
        return creatureUseCase.creature(command);
    }
    @Override
    public Creature buffCreature(Command command) {
        return creatureUseCase.buffCreature(command);
    }


    @Override
    public BuffEquipment buffEquipment(Command command) {
        return buffEquipmentUseCase.buffEquipment(command);
    }
    @Override
    public Flag flag(Command command) {
        return getFlagUseCase.flag(command);
    }
}
