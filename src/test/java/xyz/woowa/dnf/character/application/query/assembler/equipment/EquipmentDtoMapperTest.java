package xyz.woowa.dnf.character.application.query.assembler.equipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.ReinforceDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.*;
import xyz.woowa.dnf.character.domain.equipment.common.Enchant;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EquipmentDtoMapperTest {
    private final CommonValueDtoMapper commonValueDtoMapper = new CommonValueDtoMapper();
    private final HtmlMapper htmlMapper = new HtmlMapper();
    private final EquipmentDtoMapper equipmentDtoMapper = new EquipmentDtoMapper(commonValueDtoMapper, htmlMapper);

    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {
        // give
        Equipment equipment = createEquipment();

        // when
        EquipmentDto dto = equipmentDtoMapper.toMap(equipment);

        // then
        SetItemInfoDto setItemInfoDto = dto.getSetItemInfo();
        assertThat(setItemInfoDto.getBaseItem()).isEqualTo(new ItemValue("7f788a703a87d783079b41d0fe6448c9", "영원히 이어지는 황금향 세트"));
        assertThat(setItemInfoDto.getItemExplainHtml()).isEqualTo("아이템 설명");
        assertThat(setItemInfoDto.getBuffExplainHtml()).isEqualTo("<버퍼 전용 옵션><br>버프 설명");
        assertThat(setItemInfoDto.getRarity()).isEqualTo("태초");
        assertThat(setItemInfoDto.getSetPoint()).isEqualTo(2555);

        EquipmentItemDto equipmentItemDto = dto.getEquipmentItems().getFirst();
        assertThat(equipmentItemDto.getBase()).isEqualTo(
                new ItemProfileDto("무기", "태초",
                        new ItemValue("a3e557056061811337aa67eb0ef4a704", "패밀리 팔케"),
                        new ItemValue("cc886b1bcf2a72f9d7a8c9f8eba8bbca", "크로스슈터")));

        assertThat(equipmentItemDto.getReinforce().getName()).isEqualTo(ReinforceDto.enforce("13").getName());
        assertThat(equipmentItemDto.getReinforce().getValue()).isEqualTo(ReinforceDto.enforce("13").getValue());

        assertThat(equipmentItemDto.getDetail()).isEqualTo(
                new ItemDetailDto("11500",
                        "그렇지, 팔케! 오늘도 날카롭게, 정확히! 아무리 거대한 녀석도 문제없다고.",
                        "아이템 설명",
                        "힘 10",
                        "",
                        "",
                        "<버퍼 전용 옵션><br>버프 설명",
                        Tune.EMPTY)
        );


    }


    private Equipment createEquipment() {
        return new Equipment(crateSetItemInfo(), List.of(createEquipItem()));
    }

    private EquipmentItem createEquipItem() {
        ItemProfile itemProfile = new ItemProfile(Slot.WEAPON, Rarity.태초,
                new ItemName("a3e557056061811337aa67eb0ef4a704", "패밀리 팔케"),
                new ItemName("cc886b1bcf2a72f9d7a8c9f8eba8bbca", "크로스슈터"));

        return EquipmentItem.builder()
                .baseItemProfile(itemProfile)
                .skin(Skin.EMPTY)
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

    private SetItemInfo crateSetItemInfo() {
        ItemName itemName = new ItemName("7f788a703a87d783079b41d0fe6448c9", "영원히 이어지는 황금향 세트");
        Description description = new Description("아이템 설명", "버프 설명");
        return new SetItemInfo(itemName, RaritySetPoint.태초, 2555, description);
    }


}
