package xyz.woowa.dnf.character.application.query.assembler.buff.equipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.MessageFormatter;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipItemDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipmentDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.SkillInfoDto;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipItem;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.SkillInfo;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.Slot;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BuffEquipmentDtoMapperTest {

    private final CommonValueDtoMapper commonValueDtoMapper = new CommonValueDtoMapper();
    private final HtmlMapper htmlMapper = new HtmlMapper();
    private final MessageFormatter messageFormatter = new MessageFormatter();
    private final BuffEquipmentDtoMapper buffEquipmentDtoMapper = new BuffEquipmentDtoMapper(commonValueDtoMapper, htmlMapper, messageFormatter);

    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {
        // give
        BuffEquipment buffEquip = createBuffEquip();

        // when
        BuffEquipmentDto dto = buffEquipmentDtoMapper.toMap(buffEquip);

        // then
        assertThat(dto.getSkillInfo()).isEqualTo(createSkillInfoDto());

        BuffEquipItemDto buffEquipItemDto = dto.getBuffEquipItems().getFirst();
        BuffEquipItemDto expected = createBuffEquipItemDto();
        assertThat(buffEquipItemDto.getBase()).isEqualTo(expected.getBase());
        assertThat(buffEquipItemDto.getDetail()).isEqualTo(expected.getDetail());
        assertThat(buffEquipItemDto.getSkill()).isEqualTo(expected.getSkill());
        assertThat(buffEquipItemDto.getBaseStatusHtml()).isEqualTo(expected.getBaseStatusHtml());
        assertThat(buffEquipItemDto.getItemExplainHtml()).isEqualTo(expected.getItemExplainHtml());
    }

    private BuffEquipItemDto createBuffEquipItemDto() {
        ProfileDto base = new ProfileDto("무기",
                "유니크",
                new ItemValue("5a1473b65f42400a1c081d01e18d6547", "짙은 심연의 편린 크로스슈터 : 사냥을 시작해볼까!"),
                "302");

        return BuffEquipItemDto.builder()
                .base(base)
                .detail(new ItemValue("cc886b1bcf2a72f9d7a8c9f8eba8bbca", "크로스슈터"))
                .skill(NameValue.EMPTY)
                .baseStatusHtml("힘 10")
                .itemExplainHtml("사냥을 시작해볼까! 기본/스킬 공격력 증가율 0.5% 추가 증가")
                .build();
    }

    private SkillInfoDto createSkillInfoDto() {
        return new SkillInfoDto("사냥을 시작해볼까!", "20", "지속 시간 : -초<br>기본 공격 및 전직 계열 스킬 공격력 증가율 : 106%<br>이동 속도 증가 : 10%");
    }

    private BuffEquipment createBuffEquip() {
        return BuffEquipment.builder()
                .skillInfo(createSkillInfo())
                .buffEquipItems(List.of(createBuffEquipItem()))
                .build();
    }

    private BuffEquipItem createBuffEquipItem() {
        ItemProfile base = new ItemProfile(Slot.WEAPON, Rarity.유니크,
                new ItemName("5a1473b65f42400a1c081d01e18d6547", "짙은 심연의 편린 크로스슈터 : 사냥을 시작해볼까!"),
                new ItemName("cc886b1bcf2a72f9d7a8c9f8eba8bbca", "크로스슈터"));
        Detail detail = new Detail("302", "사냥을 시작해볼까! 기본/스킬 공격력 증가율 0.5% 추가 증가",
                List.of(new Status("힘", "10")));
        return BuffEquipItem.builder()
                .base(base)
                .detail(detail)
                .skills(Status.EMPTY)
                .build();
    }

    private SkillInfo createSkillInfo() {
        return new SkillInfo("사냥을 시작해볼까!", "20",
                "지속 시간 : {value1}초\n기본 공격 및 전직 계열 스킬 공격력 증가율 : {value2}%\n이동 속도 증가 : {value3}%",
                List.of("-", "106", "10"));
    }
}
