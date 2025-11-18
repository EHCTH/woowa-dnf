package xyz.woowa.dnf.character.application.query.port.inbound;

import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

public interface GetFlagUseCase {
    Flag flag(GetCharacterDetailUseCase.Command command);
}
