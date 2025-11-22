package xyz.woowa.dnf.chat.interfaces.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import xyz.woowa.dnf.chat.application.command.port.inbound.SendChatMessageUseCase;
import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;
import xyz.woowa.dnf.chat.interfaces.adapter.inbound.dto.WebSocketMessage;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SendChatMessageUseCase sendChatMessageUseCase;

    @MessageMapping("/chat/{serverId}/{characterId}")
    @SendTo("/topic/chat/{serverId}/{characterId}")
    public ChatMessageDto send(@DestinationVariable String serverId,
                               @DestinationVariable String characterId,
                               WebSocketMessage message) {
        String roomId = serverId + ":" + characterId;
        var command = new SendChatMessageUseCase.Command(roomId, message.writer(), message.content());
        return sendChatMessageUseCase.send(command);
    }
}
