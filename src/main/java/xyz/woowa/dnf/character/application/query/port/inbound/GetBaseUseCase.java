package xyz.woowa.dnf.character.application.query.port.inbound;


import xyz.woowa.dnf.character.domain.base.Base;

import java.util.List;

public interface GetBaseUseCase {
    List<Base> getAll(List<GetCharacterDetailUseCase.Command> commands);

    Base get(GetCharacterDetailUseCase.Command command);
}

