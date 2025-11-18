package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalCharacterAvatarAdapter implements ExternalAvatarPort {
    private final NeopleClient neopleClient;

    @Override
    public Avatars itemAvatar(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.AVATAR, Avatars.class, serverId, characterId);
    }

    @Override
    public BuffAvatarRow buffAvatarRow(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.BUFF_AVATAR, BuffAvatarRow.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:avatarDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, Detail.class, itemId);
    }
}
