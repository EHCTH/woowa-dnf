package xyz.woowa.dnf.chat.interfaces.adapter.inbound;

import lombok.RequiredArgsConstructor;
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

    @MessageMapping("/chat.send")
    @SendTo("/topic/chat")
    public ChatMessageDto send(WebSocketMessage message) {
        var command = new SendChatMessageUseCase.Command(message.writer(), message.content());
        return sendChatMessageUseCase.send(command);
    }
}
