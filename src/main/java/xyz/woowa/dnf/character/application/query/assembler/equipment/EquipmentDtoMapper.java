package xyz.woowa.dnf.character.application.query.assembler.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.DtoMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ReinforceDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.*;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

@Component
@RequiredArgsConstructor
public class EquipmentDtoMapper implements DtoMapper<Equipment, EquipmentDto> {
    private static final ToIntFunction<EquipmentItemDto> SLOT_KEY = equipmentItemDto ->
            Slot.orderByDisplayName(equipmentItemDto.getBase().slot());
    private static final Comparator<EquipmentItemDto> SLOT_ORDER_BY = Comparator.comparingInt(SLOT_KEY);
    private final CommonValueDtoMapper commonValueDtoMapper;
    private final HtmlMapper htmlMapper;

    @Override
    public EquipmentDto toMap(Equipment equipment) {
        SetItemInfoDto setItemInfoDto = toSetItemInfoDto(equipment.getSetItemInfo());
        List<EquipmentItemDto> equipmentItemsDto = equipment.getEquipmentItems().stream()
                .map(this::mapToEquipmentItemDto)
                .sorted(SLOT_ORDER_BY)
                .toList();
        return new EquipmentDto(setItemInfoDto, equipmentItemsDto);
    }

    private SetItemInfoDto toSetItemInfoDto(SetItemInfo setItemInfo) {
        if (setItemInfo == null || setItemInfo.itemName() == null) {
            return SetItemInfoDto.EMPTY;
        }
        ItemName itemName = setItemInfo.itemName();
        ItemValue baseItem = commonValueDtoMapper.toItemValue(itemName.id(), itemName.name());
        RaritySetPoint raritySetPoint = setItemInfo.raritySetPoint();
        return SetItemInfoDto.builder()
                .baseItem(baseItem)
                .rarity(raritySetPoint.getRarityName())
                .rarityDisplay(raritySetPoint.getDisplay())
                .setPoint(setItemInfo.setPoint())
                .itemExplainHtml(htmlMapper.itemExplainHtml(setItemInfo.description().explain()))
                .buffExplainHtml(htmlMapper.buffExplainHtml(setItemInfo.description().buffExplain()))
                .build();
    }
    private EquipmentItemDto mapToEquipmentItemDto(EquipmentItem equipmentItem) {
        ItemProfile baseItemProfile = equipmentItem.getBaseItemProfile();
        ReinForce reinForce = equipmentItem.getReinForce();
        ItemDetail itemDetail = equipmentItem.getBaseItemDetail();
        ItemProfile fusionItemProfile = equipmentItem.getFusionItemProfile();
        ItemDetail fusionItemDetail = equipmentItem.getFusionItemDetail();

        return EquipmentItemDto.builder()
                .base(toItemProfile(baseItemProfile))
                .reinforce(toReinforce(reinForce.name(), reinForce.value()))
                .detail(toBaseItemDetail(itemDetail))
                .fusion(toItemProfile(fusionItemProfile))
                .fusionDetail(toFusionItemDetail(fusionItemDetail))
                .enchant(toStatus(equipmentItem.getEnchant().getStatus()))
                .tune(toTune(equipmentItem.getTune()))
                .build();
    }
    private ReinforceDto toReinforce(String name, String value) {
        if (name.equals("강화")) {
            return ReinforceDto.enforce(value);
        }
        return ReinforceDto.dimension(name, value);
    }
    private TuneDto toTune(Tune tune) {
        return new TuneDto(tune.level(), tune.name(), toStatus(tune.status()));
    }
    private ItemDetailDto toBaseItemDetail(ItemDetail itemDetail) {
        List<NameValue> status = toStatus(itemDetail.itemStatus());
        String itemExplainHtml = htmlMapper.itemExplainHtml(itemDetail.itemExplain());
        String baseStatusHtml = htmlMapper.baseStatusHtml(status);
        String otherStatusHtml = htmlMapper.otherStatusHtml(status);
        String detailStatusHtml = htmlMapper.detailStatusHtml(status);
        String buffExplainHtml = htmlMapper.buffExplainHtml(itemDetail.buffExplain());
        return ItemDetailDto.builder()
                .fame(itemDetail.fame())
                .itemFlavorText(itemDetail.itemFlavorText())
                .itemExplainHtml(itemExplainHtml)
                .baseStatusHtml(baseStatusHtml)
                .otherStatusHtml(otherStatusHtml)
                .detailStatusHtml(detailStatusHtml)
                .buffExplainHtml(buffExplainHtml)
                .tune(itemDetail.tune())
                .build();
    }
    private ItemDetailDto toFusionItemDetail(ItemDetail fusionItemDetail) {
        return ItemDetailDto.builder()
                .fame(fusionItemDetail.fame())
                .buffExplainHtml(htmlMapper.buffExplainHtml(fusionItemDetail.buffExplain()))
                .itemFlavorText(fusionItemDetail.itemFlavorText())
                .itemExplainHtml(htmlMapper.itemExplainHtml(fusionItemDetail.itemExplain()))
                .build();
    }
    private ItemProfileDto toItemProfile(ItemProfile itemProfile) {
        ItemName itemName = itemProfile.itemName();
        ItemName itemTypeDetail = itemProfile.itemTypeDetail();
        ItemValue nameValue = commonValueDtoMapper.toItemValue(itemName.id(), itemName.name());
        ItemValue typeValue = commonValueDtoMapper.toItemValue(itemTypeDetail.id(), itemTypeDetail.name());
        return new ItemProfileDto(itemProfile.slot().getDisplayName(), itemProfile.rarity().name(), nameValue, typeValue);
    }

    private List<NameValue> toStatus(List<Status> statusList) {
        if (statusList == null || statusList.isEmpty()) {
            return Collections.emptyList();
        }
        return statusList.stream()
                .map(status -> commonValueDtoMapper.toNameValue(status.name(), status.value()))
                .toList();
    }
}
