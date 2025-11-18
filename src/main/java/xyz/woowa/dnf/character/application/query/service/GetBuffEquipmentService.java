package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.buff.equipment.BuffEquipmentMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetBuffEquipmentUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalBuffEquipmentPort;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBuffEquipmentService implements GetBuffEquipmentUseCase {
    private final ExternalBuffEquipmentPort port;
    private final BuffEquipmentMapper mapper;
    private final BoundedAsync async;


    @Override
    public BuffEquipment buffEquipment(GetCharacterDetailUseCase.Command command) {
        ExternalBuffEquipmentPort.Row row = port.row(command.serverId(), command.id());
        LinkedHashSet<String> baseIdsMap = baseIdsMap(row.equipment());
        var detail = async.toMap(baseIdsMap, port::detail);
        var merge = merge(row, detail);
        return mapper.toBuffEquipment(merge);
    }

    private LinkedHashSet<String> baseIdsMap(List<ExternalBuffEquipmentPort.Equipment> equipment) {
        return equipment.stream()
                .map(ExternalBuffEquipmentPort.Equipment::itemId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private ExternalBuffEquipmentPort.Row merge(ExternalBuffEquipmentPort.Row row, Map<String, ExternalBuffEquipmentPort.Detail> detailMap) {
        if (row.skill().buff() == null) {
            return row;
        }
        var newEquipment = row.equipment().stream()
                .map(equipment -> {
                            var detail = detailMap.get(equipment.itemId());
                            return equipment.withDetail(detail);
                        }
                ).toList();
        var buff = row.skill().buff().withEquipment(newEquipment);
        var skill = row.skill().withBuff(buff);
        return row.withSkill(skill);

    }
}
