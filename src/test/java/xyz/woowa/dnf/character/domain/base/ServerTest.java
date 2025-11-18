package xyz.woowa.dnf.character.domain.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.common.exception.DomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ServerTest {

    @Test
    @DisplayName("해당 서버 찾기")
    void 해당_서버_찾기() {

        // give && when
        Server server = Server.fromEnglish("cain");

        // then
        assertThat(Server.CAIN).isEqualTo(server);
    }

    @Test
    @DisplayName("존재하지 않는 서버면 예외가 발생한다")
    void 존재하지_않는_영문코드면_예외가_발생한다() {
        // then
        assertThatThrownBy(() -> Server.fromEnglish("NONE"))
                .isInstanceOf(DomainException.class);

    }

}
