package xyz.woowa.dnf.chat.application.query.port.inbound;

import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;

import java.util.List;

public interface GetChatMessageUseCase {
    List<ChatMessageDto> getRecent(int size);
}
