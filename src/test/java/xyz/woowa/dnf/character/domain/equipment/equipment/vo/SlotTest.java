package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlotTest {

    @Test
    @DisplayName("아이디로 장비 찾기")
    void 아이디로_장비_찾기() {
        // give
        String id = "WEAPON";

        // when
        Slot slot = Slot.findById(id);

        // then
        assertThat(slot).isEqualTo(Slot.WEAPON);


    }
    @Test
    @DisplayName("해당 장비가 없을 경우 UNKNOWN을 반환한다")
    void findById_없는_아이디면_UNKNOWN() {
        // give
        String id = "NONE";

        // when
        Slot slot = Slot.findById(id);

        // then
        assertThat(slot).isEqualTo(Slot.UNKNOWN);
    }

}
