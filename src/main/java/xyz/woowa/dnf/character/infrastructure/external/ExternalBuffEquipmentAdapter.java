package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalBuffEquipmentPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalBuffEquipmentAdapter implements ExternalBuffEquipmentPort {
    private final NeopleClient neopleClient;
    @Override
    public Row row(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.BUFF_EQUIP, Row.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:buffEquipDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, Detail.class, itemId);
    }
}
