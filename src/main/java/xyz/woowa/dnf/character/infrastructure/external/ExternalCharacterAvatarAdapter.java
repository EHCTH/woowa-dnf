package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalCharacterAvatarAdapter implements ExternalAvatarPort {
    private static final String AVATAR_PATH = "/df/servers/{serverId}/characters/{characterId}/equip/avatar";
    private static final String BUFF_AVATAR_PATH = "/df/servers/{serverId}/characters/{characterId}/skill/buff/equip/avatar";
    private static final String DETAIL_PATH  = "/df/items/{itemId}";
    private final NeopleClient neopleClient;

    @Override
    public Avatars itemAvatar(String serverId, String characterId) {
        return neopleClient.get(AVATAR_PATH, Avatars.class, serverId, characterId);
    }

    @Override
    public BuffAvatarRow buffAvatarRow(String serverId, String characterId) {
        return neopleClient.get(BUFF_AVATAR_PATH, BuffAvatarRow.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:avatarDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(DETAIL_PATH, Detail.class, itemId);
    }
}
