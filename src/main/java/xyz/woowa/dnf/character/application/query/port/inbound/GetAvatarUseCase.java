package xyz.woowa.dnf.character.application.query.port.inbound;

import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;


public interface GetAvatarUseCase {
    Avatars avatars(GetCharacterDetailUseCase.Command command);

    Avatars buffAvatars(GetCharacterDetailUseCase.Command command);
}
