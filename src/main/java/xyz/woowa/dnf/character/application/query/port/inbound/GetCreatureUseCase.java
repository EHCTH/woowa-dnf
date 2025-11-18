package xyz.woowa.dnf.character.application.query.port.inbound;

import xyz.woowa.dnf.character.domain.equipment.creature.Creature;

public interface GetCreatureUseCase {
    Creature creature(GetCharacterDetailUseCase.Command command);

    Creature buffCreature(GetCharacterDetailUseCase.Command command);
}
