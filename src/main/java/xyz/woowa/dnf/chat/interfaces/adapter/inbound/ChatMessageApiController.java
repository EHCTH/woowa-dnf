package xyz.woowa.dnf.chat.interfaces.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;
import xyz.woowa.dnf.chat.application.query.port.inbound.GetChatMessageUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatMessageApiController {
    private final GetChatMessageUseCase getChatMessageUseCase;

    @GetMapping("/messages")
    public List<ChatMessageDto> getRecentMessages(@RequestParam(defaultValue = "50") int size) {
        return getChatMessageUseCase.getRecent(size);
    }
}
