package xyz.woowa.dnf.character.application.query.assembler.flag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalFlagPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ReinForce;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.Slot;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;
import xyz.woowa.dnf.character.domain.equipment.flag.vo.Gem;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FlagMapperTest {

    private final CommonValueMapper commonValueMapper = new CommonValueMapper();
    private final FlagMapper flagMapper = new FlagMapper(commonValueMapper);

    @Test
    @DisplayName("도메인 변환 테스트")
    void 도메인_변환_테스트() {
        // give
        ExternalFlagPort.Flag flagDto = createFlagDto();

        // when
        Flag flag = flagMapper.toFlag(flagDto);

        // then
        Flag expected = createFlag();
        assertThat(flag.getBase()).isEqualTo(expected.getBase());
        assertThat(flag.getGems())
                .first()
                .extracting(Gem::base)
                .isEqualTo(expected.getGems().getFirst().base());

        assertThat(flag.getReinForce())
                .extracting(ReinForce::value)
                .isEqualTo("8");

        assertThat(flag.getDetail()).isEqualTo(expected.getDetail());
    }


    private ExternalFlagPort.Flag createFlagDto() {
        return new ExternalFlagPort.Flag(
                "6b8c084337908366810e47f2913619cf",
                "영광스러운 승리의 휘장",
                "에픽",
                "8",
                Collections.emptyList(),
                List.of(new ExternalFlagPort.Gem(
                        "9ef04aeb40ec0a392d32a836388d51b7",
                        "찬란한 용기의 젬",
                        "레전더리",
                        new ExternalFlagPort.Detail(
                                "0570ee2f9baff17105aa6e57aec0e369",
                                "젬_휘장",
                                "106",
                                List.of(new ExternalFlagPort.Status("힘", "10"))
                        )
                )),
                new ExternalFlagPort.Detail(
                        "336b7449ec80a3ea1ffe0dac9f3d49bc",
                        "휘장",
                        "708",
                        List.of(new ExternalFlagPort.Status("힘", "10")))
        );
    }

    private Flag createFlag() {
        ItemProfile base = new ItemProfile(
                Slot.FLAG,
                Rarity.에픽,
                new ItemName("6b8c084337908366810e47f2913619cf", "영광스러운 승리의 휘장"),
                new ItemName("336b7449ec80a3ea1ffe0dac9f3d49bc", "휘장"));

        Detail detail = new Detail("708", "", List.of(new Status("힘", "10")));
        Gem gem = new Gem(
                new ItemProfile(
                        Slot.GEM,
                        Rarity.레전더리,
                        new ItemName("9ef04aeb40ec0a392d32a836388d51b7", "찬란한 용기의 젬"),
                        new ItemName("0570ee2f9baff17105aa6e57aec0e369", "젬_휘장")),
                new Detail("106", "", List.of(new Status("힘", "10")))
        );
        return Flag.builder()
                .base(base)
                .detail(detail)
                .reinForce(ReinForce.enforce("8"))
                .gems(List.of(gem))
                .build();
    }
}
