package xyz.woowa.dnf.character.application.query.assembler.buff.equipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalBuffEquipmentPort;
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

class BuffEquipmentMapperTest {
    private final CommonValueMapper commonValueMapper = new CommonValueMapper();
    private final BuffEquipmentMapper buffEquipmentMapper = new BuffEquipmentMapper(commonValueMapper);

    @Test
    @DisplayName("도메인 변환 테스트")
    void 도메인_변환_테스트() {
        // give
        ExternalBuffEquipmentPort.Row row = row();

        // when
        BuffEquipment buffEquipment = buffEquipmentMapper.toBuffEquipment(row);

        // then
        assertThat(buffEquipment.getSkillInfo().name()).isEqualTo(createSkillInfo().name());
        assertThat(buffEquipment.getSkillInfo().dsec()).isEqualTo(createSkillInfo().dsec());
        assertThat(buffEquipment.getSkillInfo().level()).isEqualTo(createSkillInfo().level());

        BuffEquipItem buffEquipItem = buffEquipment.getBuffEquipItems().getFirst();
        BuffEquipItem expected = createBuffEquipItem();
        assertThat(buffEquipItem.getBase()).isEqualTo(expected.getBase());
        assertThat(buffEquipItem.getDetail()).isEqualTo(expected.getDetail());
        assertThat(buffEquipItem.getSkills()).isEqualTo(expected.getSkills());

    }


    private ExternalBuffEquipmentPort.Row row() {
        ExternalBuffEquipmentPort.SkillInfo skillInfoDto = createSkillInfoDto();
        ExternalBuffEquipmentPort.Buff buff = new ExternalBuffEquipmentPort.Buff(skillInfoDto, List.of(createEquipmentDto()));
        return new ExternalBuffEquipmentPort.Row(new ExternalBuffEquipmentPort.Skill(buff));
    }

    private ExternalBuffEquipmentPort.Equipment createEquipmentDto() {
        return new ExternalBuffEquipmentPort.Equipment(
                "WEAPON",
                "5a1473b65f42400a1c081d01e18d6547",
                "짙은 심연의 편린 크로스슈터 : 사냥을 시작해볼까!",
                "cc886b1bcf2a72f9d7a8c9f8eba8bbca",
                "크로스슈터",
                "유니크",
                null,
                new ExternalBuffEquipmentPort.Detail("302", "사냥을 시작해볼까! 기본/스킬 공격력 증가율 0.5% 추가 증가",
                        List.of(new ExternalBuffEquipmentPort.Status("힘", "10")))
        );
    }

    private  ExternalBuffEquipmentPort.SkillInfo  createSkillInfoDto() {
        return new ExternalBuffEquipmentPort.SkillInfo(
                "사냥을 시작해볼까!",
                new ExternalBuffEquipmentPort.Option("20",
                        "지속 시간 : {value1}초\n기본 공격 및 전직 계열 스킬 공격력 증가율 : {value2}%\n이동 속도 증가 : {value3}%",
                        List.of("-", "106", "10")));
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
