package xyz.woowa.dnf.chat.interfaces.adapter.inbound;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.woowa.dnf.chat.application.command.port.inbound.ClearRoomChatUseCase;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatAdminController.class)
@TestPropertySource(properties = {
        "chat.admin.token=local-token"
})
class ChatAdminControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ClearRoomChatUseCase clearRoomChatUseCase;

    @Test
    @DisplayName("잘못된 토큰이면 403을 반환한다")
    void 잘못된_토큰이면_403을_반환한다() throws Exception {
        mockMvc.perform(delete("/internal/admin/chat/rooms/cain/abc123")
                        .header("X-CHAT_ADMIN-TOKEN", "wrong-token"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(clearRoomChatUseCase);
    }

    @Test
    @DisplayName("올바른 토큰이면 방 삭제를 호출한다")
    void 올바른_토큰이면_방_삭제를_호출한다() throws Exception {
        mockMvc.perform(delete("/internal/admin/chat/rooms/cain/abc123")
                        .header("X-CHAT_ADMIN-TOKEN", "local-token"))
                .andExpect(status().isOk());

        verify(clearRoomChatUseCase).clear(
                argThat(cmd -> cmd.roomId().equals("cain:abc123")));
    }
}
