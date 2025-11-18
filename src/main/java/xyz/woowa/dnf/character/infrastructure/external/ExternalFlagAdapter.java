package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalFlagPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalFlagAdapter implements ExternalFlagPort {
    private final NeopleClient neopleClient;


    @Override
    public Row flagRow(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.FLAG, Row.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:flagDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, Detail.class, itemId);
    }
}
