package xyz.woowa.dnf.character.application.query.assembler.creature;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalCreaturePort;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreatureMapperTest {
    private final CommonValueMapper commonValueMapper = new CommonValueMapper();
    private final CreatureMapper creatureMapper = new CreatureMapper(commonValueMapper);

    @Test
    @DisplayName("도메인 변환 테스트")
    void 도메인_변환_테스트() {
        // give
        ExternalCreaturePort.Creature creatureDto = createCreatureDto();

        // when
        Creature creature = creatureMapper.toCreature(creatureDto);

        // then
        Creature expected = createCreature();
        assertThat(creature.getBase()).isEqualTo(expected.getBase());
        assertThat(creature.getClone()).isEqualTo(expected.getClone());
        assertThat(creature.getArtifacts()).isEqualTo(expected.getArtifacts());

    }


    private ExternalCreaturePort.Creature createCreatureDto() {
        return new ExternalCreaturePort.Creature(
                "20baa010279d39a04358c9935774ec1d",
                "SD 흰 구름 전령 에를리히",
                "레어",
                new ExternalCreaturePort.Clone("0ee576e63e3b8fc9b8f0015b1ea8d9bd", "SD 이누야샤"),
                List.of(new ExternalCreaturePort.Artifact(
                                "RED",
                                "38902bca473e1a2f8c91e1ffa9ea5fdd", "[이벤트]칠흑같은 황혼의 공명", "유니크",
                                new ExternalCreaturePort.Detail("아티팩트레드", "108",
                                        List.of(new ExternalCreaturePort.Status("힘", "10")),
                                        ExternalCreaturePort.CreatureInfo.EMPTY)
                        )
                ), new ExternalCreaturePort.Detail("크리쳐", "991",
                List.of(new ExternalCreaturePort.Status("힘", "10")), ExternalCreaturePort.CreatureInfo.EMPTY)
        );
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
}
