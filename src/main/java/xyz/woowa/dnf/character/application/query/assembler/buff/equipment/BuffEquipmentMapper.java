package xyz.woowa.dnf.character.application.query.assembler.buff.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalBuffEquipmentPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipItem;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.SkillInfo;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.Slot;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BuffEquipmentMapper {
    private final CommonValueMapper mapper;
    public BuffEquipment toBuffEquipment(ExternalBuffEquipmentPort.Row row) {
        List<ExternalBuffEquipmentPort.Equipment> equipment = row.equipment();
        List<BuffEquipItem> buffEquipItems = equipment.stream()
                .map(this::toBuffEquipItem)
                .toList();

        return BuffEquipment.builder()
                .skillInfo(toSkillInfo(row.skill().buff()))
                .buffEquipItems(buffEquipItems)
                .build();
    }

    private SkillInfo toSkillInfo(ExternalBuffEquipmentPort.Buff buff) {
        if (buff == null) {
            return SkillInfo.EMPTY;
        }
        ExternalBuffEquipmentPort.SkillInfo skillInfo = buff.skillInfo();
        ExternalBuffEquipmentPort.Option option = skillInfo.option();
        if (option == null) {
            return SkillInfo.EMPTY;
        }
        List<String> paddingValues = new ArrayList<>(option.values());
        paddingValues.addAll(List.of("-", "-", "-", "-", "-", "-", "-"));
        return new SkillInfo(
                skillInfo.name(),
                option.level(),
                option.desc(),
                paddingValues);

    }
    private BuffEquipItem toBuffEquipItem(ExternalBuffEquipmentPort.Equipment equipment) {
        return BuffEquipItem.builder()
                .base(toItemProfile(equipment))
                .skills(toSkills(equipment.enchant()))
                .detail(toDetail(equipment.detail()))
                .build();

    }

    private Detail toDetail(ExternalBuffEquipmentPort.Detail detail) {
        List<Status> status = detail.itemStatus()
                .stream()
                .map(stat -> mapper.toNameStatus(stat.name(), stat.value()))
                .toList();
        return new Detail(detail.fame(), detail.itemExplain(), status);

    }
    private Status toSkills(ExternalBuffEquipmentPort.Enchant enchant) {
        if (enchant == null || !enchant.hasSkills()) {
            return Status.EMPTY;
        }
        return mapper.toNameStatus(enchant.name(), enchant.value());
    }
    private ItemProfile toItemProfile(ExternalBuffEquipmentPort.Equipment equipment) {
        return ItemProfile.builder()
                .slot(Slot.findById(equipment.slotId()))
                .rarity(Rarity.findByValue(equipment.itemRarity()))
                .itemName(mapper.toItemName(equipment.itemId(), equipment.itemName()))
                .itemTypeDetail(mapper.toItemName(equipment.itemTypeDetailId(),equipment.itemTypeDetail()))
                .build();

    }
}
