package xyz.woowa.dnf.character.domain.equipment.avatar.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EmblemSlotTest {

    @Test
    @DisplayName("색상으로 엠블렘 슬롯 찾기")
    void 색상으로_엠블렘_슬롯_찾기() {
        // give
        String color = "붉은빛";

        // when
        EmblemSlot slot = EmblemSlot.findByColor(color);

        // then
        assertThat(slot).isEqualTo(EmblemSlot.RED);
    }

    @Test
    @DisplayName("해당 색상 엠블렘 슬롯이 없을경우 EMPTY 반환")
    void 해당_색상_엠블렘_슬롯이_없을경우_EMPTY_반환() {
        // give
        String color = "보라빛";

        // when
        EmblemSlot slot = EmblemSlot.findByColor(color);

        // then
        assertThat(slot).isEqualTo(EmblemSlot.EMPTY);
    }

    @Test
    @DisplayName("이름으로 엠블렘 슬롯 찾기")
    void 이름으로_엠블렘_슬롯_찾기() {
        // give
        String name = "붉은빛의 힘 엠블렘";

        // when
        EmblemSlot slot = EmblemSlot.findByName(name);

        // then
        assertThat(slot).isEqualTo(EmblemSlot.RED);
    }

    @Test
    @DisplayName("해당 이름 엠블렘 슬롯이 없을 경우 EMPTY 반환")
    void 해당_이름_엠블렘_슬롯이_없을경우_EMPTY반환() {
        // give
        String name = "EMPTY";

        // when
        EmblemSlot slot = EmblemSlot.findByName(name);

        // then
        assertThat(slot).isEqualTo(EmblemSlot.EMPTY);
    }
}
