package xyz.woowa.dnf.chat.application.command.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.woowa.dnf.chat.application.assembler.ChatDtoMapper;
import xyz.woowa.dnf.chat.application.command.port.inbound.SendChatMessageUseCase;
import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.domain.ChatMessage;

@Service
@RequiredArgsConstructor
public class ChatMessageService implements SendChatMessageUseCase {
    private final ChatMessageStorePort storePort;
    private final ChatDtoMapper mapper;

    @Override
    @Transactional
    public ChatMessageDto send(Command command) {
        ChatMessage chatMessage = ChatMessage.create(command.roomId(), command.writer(), command.content());
        ChatMessage saved = storePort.save(chatMessage);
        return mapper.toMap(saved);
    }
}
