package xyz.woowa.dnf.character.application.query.assembler.flag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.common.ReinforceDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.flag.FlagDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.flag.GemDto;
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


class FlagDtoMapperTest {
    private final CommonValueDtoMapper commonValueDtoMapper = new CommonValueDtoMapper();
    private final HtmlMapper htmlMapper = new HtmlMapper();
    private final FlagDtoMapper flagDtoMapper = new FlagDtoMapper(commonValueDtoMapper, htmlMapper);

    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {
        // give
        Flag flag = createFlag();
        // when
        FlagDto dto = flagDtoMapper.toMap(flag);

        // then
        assertThat(dto.getBase()).isEqualTo(new ProfileDto("휘장", "에픽",
                new ItemValue("6b8c084337908366810e47f2913619cf", "영광스러운 승리의 휘장"),
                new ItemValue("336b7449ec80a3ea1ffe0dac9f3d49bc", "휘장"), "708"));

        assertThat(dto.getGems())
                .first()
                .extracting(GemDto::getBase)
                .isEqualTo(new ProfileDto("젬", "레전더리",
                        new ItemValue("9ef04aeb40ec0a392d32a836388d51b7", "찬란한 용기의 젬"),
                        new ItemValue("0570ee2f9baff17105aa6e57aec0e369", "젬_휘장"),
                        "106"));

        assertThat(dto.getReinforce()).extracting(ReinforceDto::getValue).isEqualTo("8");

    }

    private Flag createFlag() {
        ItemProfile base = new ItemProfile(
                Slot.FLAG,
                Rarity.에픽,
                new ItemName("6b8c084337908366810e47f2913619cf", "영광스러운 승리의 휘장"),
                new ItemName("336b7449ec80a3ea1ffe0dac9f3d49bc", "휘장"));

        Detail detail = new Detail("708", "", Collections.emptyList());
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
