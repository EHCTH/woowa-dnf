package xyz.woowa.dnf.character.application.query.assembler.creature;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.ArtifactDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.CreatureDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.ProfileDto;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.*;

import java.util.Collections;
import java.util.List;


class CreatureDtoMapperTest {
    private final CommonValueDtoMapper commonValueDtoMapper = new CommonValueDtoMapper();
    private final HtmlMapper htmlMapper = new HtmlMapper();
    private final CreatureDtoMapper creatureDtoMapper = new CreatureDtoMapper(commonValueDtoMapper, htmlMapper);

    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {
        // give
        Creature creature = createCreature();

        // when
        CreatureDto dto = creatureDtoMapper.toMap(creature);

        // then
        CreatureDto expected = createCreatureDto();

        Assertions.assertThat(dto.getBase()).isEqualTo(expected.getBase());
        Assertions.assertThat(dto.getClone()).isEqualTo(expected.getClone());
        Assertions.assertThat(dto.getArtifacts()).isEqualTo(expected.getArtifacts());



    }

    private Creature createCreature() {
        CreatureProfile base = new CreatureProfile(
                new ItemName(
                        "20baa010279d39a04358c9935774ec1d", "SD 흰 구름 전령 에를리히"),
                "991",
                Rarity.레어);


        return Creature.builder()
                .base(base)
                .clone(new ItemName("0ee576e63e3b8fc9b8f0015b1ea8d9bd", "SD 이누야샤"))
                .artifacts(createArtifacts())
                .detail(new Detail(new CreatureSkill("스킬", "설명"),
                        new CreatureSkill("스킬", "설명"), Collections.emptyList(), "크리쳐"))
                .build();
    }

    private Artifacts createArtifacts() {
        return new Artifacts(List.of(new Artifact(ArtifactSlot.RED,
                new ItemName("38902bca473e1a2f8c91e1ffa9ea5fdd", "[이벤트]칠흑같은 황혼의 공명"),
                Rarity.유니크,
                "108",
                "아티팩트레드", List.of(new Status("힘", "10"))
                )));
    }

    private CreatureDto createCreatureDto() {
        ProfileDto base = new ProfileDto(new ItemValue(
                "20baa010279d39a04358c9935774ec1d",
                "SD 흰 구름 전령 에를리히"), "레어", "991");
        return CreatureDto.builder()
                .base(base)
                .clone(new ItemValue("0ee576e63e3b8fc9b8f0015b1ea8d9bd", "SD 이누야샤"))
                .artifacts(List.of(new ArtifactDto("RED", new ProfileDto(
                        new ItemValue("38902bca473e1a2f8c91e1ffa9ea5fdd", "[이벤트]칠흑같은 황혼의 공명"),
                        "유니크","108"),
                        "아티팩트레드", "힘 10", "","")))
                .build();
    }
}
