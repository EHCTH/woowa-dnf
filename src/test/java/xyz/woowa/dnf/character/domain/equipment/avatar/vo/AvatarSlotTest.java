package xyz.woowa.dnf.character.domain.equipment.avatar.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AvatarSlotTest {

    @Test
    @DisplayName("아바타 슬롯 찾기")
    void 아바타_슬롯_찾기() {
        // give
        String id = "HAIR";

        // when
        AvatarSlot slot = AvatarSlot.findById(id);

        // then
        assertThat(slot).isEqualTo(AvatarSlot.HAIR);
    }

    @Test
    @DisplayName("해당 아바타가 없을 경우 EMPTY 반환")
    void 해당_아바타가_없을_경우_EMPTY_반환() {
        // give
        String id = "UNKNOWN";

        // when
        AvatarSlot slot = AvatarSlot.findById(id);

        // then
        assertThat(slot).isEqualTo(AvatarSlot.EMPTY);
    }

    @Test
    @DisplayName("EMPTY일 때만 hasEmpty가 true이다")
    void hasEmpty는_EMPTY에서만_true() {
        Assertions.assertThat(AvatarSlot.EMPTY.hasEmpty()).isTrue();
        Assertions.assertThat(AvatarSlot.HAIR.hasEmpty()).isFalse();
    }
}
