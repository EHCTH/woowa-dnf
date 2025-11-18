package xyz.woowa.dnf.character.application.query.assembler.avatar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatar;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.AvatarSlot;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Emblem;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarMapperTest {
    private final CommonValueMapper commonValueMapper = new CommonValueMapper();
    private final AvatarMapper avatarMapper = new AvatarMapper(commonValueMapper);

    @Test
    @DisplayName("도메인 변환 테스트")
    void 도메인_변환_테스트() {
        ExternalAvatarPort.Avatar externalAvatar = createExternalAvatar();

        Avatar avatar = avatarMapper.toAvatar(externalAvatar);

        // then
        ItemProfile base = avatar.getBase();
        Detail detail = avatar.getDetail();

        assertThat(base.avatarSlot()).isEqualTo(AvatarSlot.HEADGEAR);
        assertThat(base.id()).isEqualTo("2defdc2422ff3c61f5ac1af583e79459");
        assertThat(base.name()).isEqualTo("레어 모자 클론 아바타");
        assertThat(base.rarity()).isEqualTo(Rarity.레어);

        assertThat(avatar.getClone().id()).isEqualTo("35be443a612ef75de930235e9909e40e");
        assertThat(avatar.getClone().name()).isEqualTo("토도 카스미의 모자 투명아바타");

        assertThat(avatar.getOptionAbility()).isEqualTo("캐스팅 속도 14.0% 증가");

        List<Emblem> emblems = avatar.emblems();
        assertThat(emblems).hasSize(2);

        Emblem firstEmblem = emblems.getFirst();
        assertThat(firstEmblem.base().id()).isEqualTo("9aec2299f27c4b15441b947087c9609c");
        assertThat(firstEmblem.base().name()).isEqualTo("찬란한 붉은빛 엠블렘[힘]");
        assertThat(firstEmblem.rarity()).isEqualTo(Rarity.유니크);

        assertThat(detail.fame()).isEqualTo("10");
        assertThat(detail.itemExplain()).isEqualTo("설명");

        List<Status> statusList = detail.status();
        assertThat(statusList).hasSize(1);
        Status status = statusList.getFirst();
        assertThat(status.name()).isEqualTo("힘");
        assertThat(status.value()).isEqualTo("10");
    }
    private ExternalAvatarPort.Avatar createExternalAvatar() {
        ExternalAvatarPort.Clone clone = new ExternalAvatarPort.Clone(
                "35be443a612ef75de930235e9909e40e",
                "토도 카스미의 모자 투명아바타"
        );

        ExternalAvatarPort.Emblem emblem1 = new ExternalAvatarPort.Emblem(
                "1",
                "붉은빛",
                "9aec2299f27c4b15441b947087c9609c",
                "찬란한 붉은빛 엠블렘[힘]",
                "유니크"
        );

        ExternalAvatarPort.Emblem emblem2 = new ExternalAvatarPort.Emblem(
                "2",
                "붉은빛",
                "9aec2299f27c4b15441b947087c9609c",
                "찬란한 붉은빛 엠블렘[힘]",
                "유니크"
        );

        ExternalAvatarPort.Status status = new ExternalAvatarPort.Status("힘", "10");
        ExternalAvatarPort.Detail detail = new ExternalAvatarPort.Detail(
                "10",
                "설명",
                List.of(status)
        );

        // 최종 Avatar 레코드 생성
        return new ExternalAvatarPort.Avatar(
                "HEADGEAR",
                "모자 아바타",
                "2defdc2422ff3c61f5ac1af583e79459",
                "레어 모자 클론 아바타",
                "레어",
                clone,
                "캐스팅 속도 14.0% 증가",
                List.of(emblem1, emblem2),
                detail
        );
    }


}
