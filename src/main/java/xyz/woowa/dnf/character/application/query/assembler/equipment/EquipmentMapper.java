package xyz.woowa.dnf.character.application.query.assembler.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalEquipmentPort;
import xyz.woowa.dnf.character.domain.equipment.common.Enchant;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EquipmentMapper {
    private final CommonValueMapper commonValueMapper;

    public Equipment toDomain(List<ExternalEquipmentPort.SetItem> setItems, List<ExternalEquipmentPort.Item> items) {
        SetItemInfo domainSetItemInfo = toDomainSetItemInfo(setItems);
        List<EquipmentItem> equipmentItems = items.stream().map(this::toDomainEquipmentItem).toList();
        return new Equipment(domainSetItemInfo, equipmentItems);
    }

    public SetItemInfo toDomainSetItemInfo(List<ExternalEquipmentPort.SetItem> setItems) {
        if (setItems == null || setItems.isEmpty()) {
            return SetItemInfo.EMPTY;
        }
        ExternalEquipmentPort.SetItem setItem = setItems.getFirst();
        ItemName itemName = commonValueMapper.toItemName(setItem.setItemId(), setItem.setItemName());
        ExternalEquipmentPort.Active active = setItem.active();
        if (active.setPoint() == null) {
            return new SetItemInfo(itemName,RaritySetPoint.NONE, active.setEquipCount(), toDescription(active));
        }
        return new SetItemInfo(itemName,
                RaritySetPoint.computeSetPoint(active.setPoint().current()),
                active.setPoint().current(),
                toDescription(active));
    }

    private Description toDescription(ExternalEquipmentPort.Active active) {
        if (active == null) {
            return Description.EMPTY;
        }
        if (active.explain() == null && active.buffExplain() == null) {
            return Description.EMPTY;
        }
        if (active.explain() == null) {
            return new Description("", active.buffExplain());
        }
        if (active.buffExplain() == null) {
            return new Description(active.explain(), "");
        }
        return new Description(active.explain(), active.buffExplain());
    }


    public EquipmentItem toDomainEquipmentItem(ExternalEquipmentPort.Item item) {
        Tune tune = toTune(item.tune());
        Enchant enchant = toEnchant(item.enchant());
        ItemDetail itemDetail = toItemDetail(item.itemDetail());
        return EquipmentItem.builder()
                .baseItemProfile(toItemProfile(item))
                .skin(toSkin(item.skin()))
                .baseItemDetail(itemDetail)
                .fusionItemProfile(toFusionItemProfile(item, item.upgradeInfo(), item.upgradeInfoDetail()))
                .fusionItemDetail(toFusionItemDetail(item.fusionOption(), item.upgradeInfoDetail()))
                .reinForce(toReinforce(item))
                .tune(tune)
                .enchant(enchant)
                .build();
    }
    private Skin toSkin(ExternalEquipmentPort.Skin skin) {
        if (skin == null) {
            return Skin.EMPTY;
        }
        return new Skin(Slot.SKIN_WEAPON, new ItemName(skin.itemId(), skin.itemName()), Rarity.findByValue(skin.itemRarity()));
    }

    private ItemProfile toItemProfile(ExternalEquipmentPort.Item item) {
        return ItemProfile.builder()
                .slot(Slot.findById(item.slotId()))
                .rarity(Rarity.findByValue(item.itemRarity().name()))
                .itemName(commonValueMapper.toItemName(item.itemId(), item.itemName()))
                .itemTypeDetail(commonValueMapper.toItemName(item.itemTypeDetailId(), item.itemTypeDetail()))
                .build();
    }
    private ItemProfile toFusionItemProfile(ExternalEquipmentPort.Item item,
                                            ExternalEquipmentPort.UpgradeInfo upgradeInfo,
                                            ExternalEquipmentPort.UpgradeInfoDetail upgradeInfoDetail) {
        if (upgradeInfo == null) {
            return ItemProfile.EMPTY;
        }
        return ItemProfile.builder()
                .slot(Slot.findById(item.slotId()))
                .rarity(Rarity.findByValue(upgradeInfo.itemRarity()))
                .itemName(commonValueMapper.toItemName(upgradeInfo.itemId(), upgradeInfo.itemName()))
                .itemTypeDetail(commonValueMapper.toItemName(upgradeInfoDetail.itemTypeDetailId(), upgradeInfoDetail.itemTypeDetail()))
                .build();
    }


    private ReinForce toReinforce(ExternalEquipmentPort.Item item) {
        if (item.reinforce() == null || item.amplificationName().isEmpty()) {
            return ReinForce.enforce(item.reinforce());
        }
        return new ReinForce(item.amplificationName(), item.reinforce());
    }

    private Enchant toEnchant(ExternalEquipmentPort.Enchant enchant) {
        if (enchant == null) {
            return new Enchant(Collections.emptyList());
        }
        List<ExternalEquipmentPort.Stat> stats = new ArrayList<>();
        stats.addAll(enchant.getStatus());
        stats.addAll(enchant.getSkills());
        return new Enchant(toStatus(stats));
    }

    private ItemDetail toItemDetail(ExternalEquipmentPort.ItemDetail itemDetail) {
        List<Status> status = toStatus(itemDetail.itemStatus());
        Tune tune = toTune(itemDetail.tune());
        return ItemDetail.builder()
                .buffExplain(itemDetail.getItemBuff())
                .itemExplain(itemDetail.itemExplain())
                .itemFlavorText(itemDetail.itemFlavorText())
                .fame(itemDetail.fame())
                .itemStatus(status)
                .tune(tune)
                .build();
    }
    private ItemDetail toFusionItemDetail(ExternalEquipmentPort.FusionOption fusionOption,
                                          ExternalEquipmentPort.UpgradeInfoDetail upgradeInfoDetail) {
        if (upgradeInfoDetail == null) {
            return ItemDetail.EMPTY;
        }
        FusionOption fusionOptionValue = toFusionOption(fusionOption);
        List<Status> status = toStatus(upgradeInfoDetail.itemStatus());
        return ItemDetail.builder()
                .buffExplain(fusionOptionValue.buffExplain())
                .itemExplain(fusionOptionValue.itemExplain())
                .itemFlavorText(upgradeInfoDetail.itemFlavorText())
                .fame(upgradeInfoDetail.fame())
                .itemStatus(status)
                .tune(Tune.EMPTY)
                .build();
    }

    private Tune toTune(List<ExternalEquipmentPort.Tune> tunes) {
        if (tunes == null || tunes.isEmpty()) {
            return new Tune(0, 0, Collections.emptyList(), "");
        }
        if (tunes.size() == 2) {
            ExternalEquipmentPort.Tune firstTune = tunes.getFirst();
            ExternalEquipmentPort.Tune tune = tunes.get(1);
            return new Tune(firstTune.level(), 0, toStatus(tune.status()), tune.name());

        }
        ExternalEquipmentPort.Tune tune = tunes.getFirst();
        return new Tune(tune.level(), tune.setPoint(), toStatus(tune.status()), "");
    }

    private List<Status> toStatus(List<ExternalEquipmentPort.Stat> stats) {
        if (stats == null || stats.isEmpty()) {
            return Collections.emptyList();
        }
        return stats.stream()
                .map(stat -> new Status(stat.name(), stat.value()))
                .toList();
    }

    private FusionOption toFusionOption(ExternalEquipmentPort.FusionOption fusionOption) {
        if (fusionOption == null || fusionOption.options().isEmpty()) {
            return FusionOption.EMPTY;
        }
        ExternalEquipmentPort.FusionOption.Option first = fusionOption.options().getFirst();
        return new FusionOption(first.buff(), first.explain(), first.buffExplain());
    }
}
