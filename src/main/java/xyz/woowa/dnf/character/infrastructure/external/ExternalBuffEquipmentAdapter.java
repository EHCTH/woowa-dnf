package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalBuffEquipmentPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalBuffEquipmentAdapter implements ExternalBuffEquipmentPort {
    private static final String BUFF_EQUIPMENT_PATH = "df/servers/{serverId}/characters/{characterId}/skill/buff/equip/equipment";
    private static final String DETAIL_PATH  = "/df/items/{itemId}";
    private final NeopleClient neopleClient;
    @Override
    public Row row(String serverId, String characterId) {
        return neopleClient.get(BUFF_EQUIPMENT_PATH, Row.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:buffEquipDetail", key = "#itemId", unless = "#result == null")
    public Detail detail(String itemId) {
        return neopleClient.get(DETAIL_PATH, Detail.class, itemId);
    }
}
