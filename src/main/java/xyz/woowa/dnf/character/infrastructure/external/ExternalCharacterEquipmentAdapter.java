package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalEquipmentPort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalCharacterEquipmentAdapter implements ExternalEquipmentPort {
    private final NeopleClient neopleClient;

    @Override
    public EquipmentPayload fetchPayload(String serverId, String characterId) {
        return neopleClient.get(NeoplePaths.EQUIP, EquipmentPayload.class, serverId, characterId);
    }

    @Override
    @Cacheable(cacheNames = "neople:itemDetail", key = "#itemId", unless = "#result == null")
    public ItemDetail itemDetail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, ItemDetail.class, itemId);
    }

    @Override
    @Cacheable(cacheNames = "neople:upgradeDetail", key = "#itemId", unless = "#result == null")
    public UpgradeInfoDetail upgradeInfoDetail(String itemId) {
        return neopleClient.get(NeoplePaths.DETAIL, UpgradeInfoDetail.class, itemId);
    }
}
