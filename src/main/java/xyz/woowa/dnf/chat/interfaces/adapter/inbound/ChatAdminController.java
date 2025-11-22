package xyz.woowa.dnf.chat.interfaces.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.woowa.dnf.chat.application.command.port.inbound.ClearRoomChatUseCase;

@RestController
@RequestMapping("/internal/admin/chat")
@RequiredArgsConstructor
public class ChatAdminController {
    private final ClearRoomChatUseCase clearRoomChatUseCase;

    @Value("${chat.admin.token}")
    private String adminToken;

    @DeleteMapping("/rooms/{serverId}/{characterId}")
    public void clearRoom(@RequestHeader("X-CHAT_ADMIN-TOKEN") String token,
                          @PathVariable String serverId,
                          @PathVariable String characterId) {
        if (!adminToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 토큰 불일치");
        }

        String roomId = serverId + ":" + characterId;
        clearRoomChatUseCase.clear(new ClearRoomChatUseCase.Command(roomId));
    }
}
