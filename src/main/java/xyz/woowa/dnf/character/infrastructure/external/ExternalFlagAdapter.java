package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalFlagPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalFlagAdapter implements ExternalFlagPort {
    private static final String FLAG_PATH = "/df/servers/{serverId}/characters/{characterId}/equip/flag";
    private static final String DETAIL_PATH  = "/df/items/{itemId}";
    private final NeopleClient neopleClient;


    @Override
    public Row flagRow(String serverId, String characterId) {
        return neopleClient.get(FLAG_PATH, Row.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:flagDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(DETAIL_PATH, Detail.class, itemId);
    }
}
