package xyz.woowa.dnf.character.domain.equipment.creature.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ArtifactSlotTest {

    @Test
    @DisplayName("색상으로 아티팩트 슬롯 찾기")
    void 색상으로_아티팩트_슬롯_찾기() {
        // give
        String red = "RED";
        String blue = "BLUE";
        String green = "GREEN";

        // when
        ArtifactSlot redSlot = ArtifactSlot.findByColor(red);
        ArtifactSlot blueSlot = ArtifactSlot.findByColor(blue);
        ArtifactSlot greenSlot = ArtifactSlot.findByColor(green);

        // then
        assertThat(redSlot).isEqualTo(ArtifactSlot.RED);
        assertThat(blueSlot).isEqualTo(ArtifactSlot.BLUE);
        assertThat(greenSlot).isEqualTo(ArtifactSlot.GREEN);
    }

    @Test
    @DisplayName("해당 색상이 없을경우 EMPTY를 반환한다")
    void 해당_색상이_없을경우_EMPTY를_반환한다() {
        // give
        String color = "UNKNOWN";

        // when
        ArtifactSlot slot = ArtifactSlot.findByColor(color);

        // then
        assertThat(slot).isEqualTo(ArtifactSlot.EMPTY);
    }
}
