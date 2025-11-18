package xyz.woowa.dnf.character.application.query.assembler.equipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalEquipmentPort;
import xyz.woowa.dnf.character.domain.equipment.common.Enchant;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EquipmentMapperTest {
    private final CommonValueMapper commonValueMapper = new CommonValueMapper();
    private final EquipmentMapper equipmentMapper = new EquipmentMapper(commonValueMapper);

    @Test
    @DisplayName("도메인 변환 테스트")
    void 도메인_변환_테스트() {
        // give
        List<ExternalEquipmentPort.SetItem> setItemDto = createSetItemDto();
        List<ExternalEquipmentPort.Item> itemDto = createItemDto();

        // when
        Equipment equipment = equipmentMapper.toDomain(setItemDto, itemDto);

        // then
        SetItemInfo setItemInfo = equipment.getSetItemInfo();
        SetItemInfo expectedSetItemInfo = createSetItemInfo();
        assertThat(setItemInfo.itemName()).isEqualTo(expectedSetItemInfo.itemName());
        assertThat(setItemInfo.raritySetPoint()).isEqualTo(expectedSetItemInfo.raritySetPoint());
        assertThat(setItemInfo.setPoint()).isEqualTo(expectedSetItemInfo.setPoint());
        assertThat(setItemInfo.description()).isEqualTo(expectedSetItemInfo.description());

        EquipmentItem equipmentItem = equipment.getEquipmentItems().getFirst();
        EquipmentItem expectedEquipItem = createEquipItem();
        assertThat(equipmentItem.getBaseItemProfile()).isEqualTo(expectedEquipItem.getBaseItemProfile());
        assertThat(equipmentItem.getBaseItemDetail()).isEqualTo(expectedEquipItem.getBaseItemDetail());
        assertThat(equipmentItem.getReinForce()).isEqualTo(expectedEquipItem.getReinForce());
        assertThat(equipmentItem.getEnchant()).isEqualTo(expectedEquipItem.getEnchant());
    }


    private List<ExternalEquipmentPort.SetItem> createSetItemDto() {
        var setItem = new ExternalEquipmentPort.SetItem(
                "7f788a703a87d783079b41d0fe6448c9",
                "영원히 이어지는 황금향 세트",
                "태초",
                new ExternalEquipmentPort.Active("아이템 설명",
                        "버프 설명",
                        Collections.emptyList(),
                        new ExternalEquipmentPort.SetPointRange(2555, 0, 0), 0));
        return List.of(setItem);
    }

    private List<ExternalEquipmentPort.Item> createItemDto() {
        var item = new ExternalEquipmentPort.Item(
                "WEAPON",
                "무기",
                "a3e557056061811337aa67eb0ef4a704",
                "패밀리 팔케",
                "4ffb6f14b86f5c818a925bf58022686e",
                "무기",
                "cc886b1bcf2a72f9d7a8c9f8eba8bbca",
                "크로스슈터",
                ExternalEquipmentPort.Rarity.태초,
                "", "", "13", "",
                new ExternalEquipmentPort.Enchant(
                        List.of(new ExternalEquipmentPort.Stat("힘", "10")),
                        Collections.emptyList()),
                List.of(new ExternalEquipmentPort.Stat("힘", "10")),
                Collections.emptyList(),
                null,
                null,
                new ExternalEquipmentPort.ItemDetail(
                        "아이템 설명", "아이템 설명",
                        "11500", "그렇지, 팔케! 오늘도 날카롭게, 정확히! 아무리 거대한 녀석도 문제없다고.",
                        List.of(new ExternalEquipmentPort.Stat("힘", "10")),
                        Collections.emptyList(),
                        new ExternalEquipmentPort.ItemBuff("버프 설명"))
                , null);
        return List.of(item);
    }

    private EquipmentItem createEquipItem() {
        ItemProfile itemProfile = new ItemProfile(Slot.WEAPON, Rarity.태초,
                new ItemName("a3e557056061811337aa67eb0ef4a704", "패밀리 팔케"),
                new ItemName("cc886b1bcf2a72f9d7a8c9f8eba8bbca", "크로스슈터"));

        return EquipmentItem.builder()
                .baseItemProfile(itemProfile)
                .reinForce(ReinForce.enforce("13"))
                .baseItemDetail(
                        new ItemDetail(
                                "버프 설명", "아이템 설명",
                                "그렇지, 팔케! 오늘도 날카롭게, 정확히! 아무리 거대한 녀석도 문제없다고.",
                                "11500",
                                List.of(new Status("힘", "10")),
                                Tune.EMPTY)
                )
                .fusionItemProfile(
                        ItemProfile.EMPTY
                )
                .fusionItemDetail(
                        ItemDetail.EMPTY
                )
                .enchant(new Enchant(List.of(new Status("힘", "10"))))
                .tune(Tune.EMPTY)
                .build();
    }

    private SetItemInfo createSetItemInfo() {
        ItemName itemName = new ItemName("7f788a703a87d783079b41d0fe6448c9", "영원히 이어지는 황금향 세트");
        Description description = new Description("아이템 설명", "버프 설명");
        return new SetItemInfo(itemName, RaritySetPoint.태초, 2555, description);
    }


}
