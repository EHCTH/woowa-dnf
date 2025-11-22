package xyz.woowa.dnf.chat.application.assembler;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.DtoMapper;
import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;
import xyz.woowa.dnf.chat.domain.ChatMessage;

@Component
public class ChatDtoMapper implements DtoMapper<ChatMessage, ChatMessageDto> {
    @Override
    public ChatMessageDto toMap(ChatMessage chatMessage) {
        return new ChatMessageDto(
                chatMessage.getId(),
                chatMessage.getWriter(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt());
    }
}
