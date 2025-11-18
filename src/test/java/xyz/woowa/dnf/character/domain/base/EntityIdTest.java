package xyz.woowa.dnf.character.domain.base;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class EntityIdTest {

    @Test
    @DisplayName("서버와 캐릭터 ID가 같으면 동일하다")
    void 서버와_캐릭터ID가_같으면_동일하다() {
        // give
        EntityId id1 = new EntityId("abc", Server.CAIN);

        // when
        EntityId id2 = new EntityId("abc", Server.CAIN);

        // then
        Assertions.assertThat(id1).isEqualTo(id2);
        Assertions.assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
}
