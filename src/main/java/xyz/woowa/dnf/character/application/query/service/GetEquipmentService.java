package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.equipment.EquipmentMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetEquipmentUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalEquipmentPort;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetEquipmentService implements GetEquipmentUseCase {
    private final EquipmentMapper equipmentMapper;
    private final ExternalEquipmentPort port;
    private final BoundedAsync async;

    @Override
    public Equipment equipment(GetCharacterDetailUseCase.Command command) {
        return equipmentDetailMerge(command);
    }

    private Equipment equipmentDetailMerge(GetCharacterDetailUseCase.Command command) {
        var payload = port.fetchPayload(command.serverId(), command.id());
        var items = payload.equipment()
                .stream()
                .toList();

        var itemIds = baseIdsMap(items);
        var fusionIds = fusionIdsMap(items);

        var detailMap = async.toMap(itemIds, port::itemDetail);
        var fusionMap = async.toMap(fusionIds, port::upgradeInfoDetail);

        var merge = equipmentMerge(items, detailMap, fusionMap);
        return equipmentMapper.toDomain(payload.setItemInfo(), merge);
    }

    private LinkedHashSet<String> baseIdsMap(List<ExternalEquipmentPort.Item> items) {
        return items.stream().map(ExternalEquipmentPort.Item::itemId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private LinkedHashSet<String> fusionIdsMap(List<ExternalEquipmentPort.Item> items) {
        return items.stream().map(ExternalEquipmentPort.Item::upgradeInfo)
                .filter(Objects::nonNull)
                .map(ExternalEquipmentPort.UpgradeInfo::itemId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private List<ExternalEquipmentPort.Item> equipmentMerge(
            List<ExternalEquipmentPort.Item> items,
            Map<String, ExternalEquipmentPort.ItemDetail> detailMap,
            Map<String, ExternalEquipmentPort.UpgradeInfoDetail> upMap) {

        return items.stream().map(it -> {
            var withBase = Optional.ofNullable(detailMap.get(it.itemId()))
                    .map(it::withItemDetail)
                    .orElse(it);

            if (it.upgradeInfo() == null) {
                return withBase;
            }

            return upgradeItemMerge(upMap, it, withBase);

        }).toList();
    }

    private ExternalEquipmentPort.Item upgradeItemMerge(Map<String, ExternalEquipmentPort.UpgradeInfoDetail> upMap, ExternalEquipmentPort.Item it, ExternalEquipmentPort.Item withBase) {
        var up = upMap.get(it.upgradeInfo().itemId());
        if (up == null) {
            return withBase;
        }
        return withBase.withUpgradeInfoDetail(up);
    }
}
