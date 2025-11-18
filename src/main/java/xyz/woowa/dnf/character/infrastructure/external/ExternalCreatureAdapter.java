package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalCreaturePort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalCreatureAdapter implements ExternalCreaturePort {
    private final NeopleClient neopleClient;

    @Override
    public Row row(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.CREATURE, Row.class, serverId, characterId);
    }

    @Override
    public BuffRow buffRow(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.BUFF_CREATURE, BuffRow.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:creatureDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, Detail.class, itemId);
    }

    @Cacheable(cacheNames = "neople:artifactDetail", key = "#itemId", unless = "#result == null") @Override
    public Detail artifactDetail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, Detail.class, itemId);
    }
}
