package xyz.woowa.dnf.chat.application.query.port.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.chat.application.assembler.ChatDtoMapper;
import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.application.query.port.inbound.GetChatMessageUseCase;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetChatMessageService implements GetChatMessageUseCase {
    private final ChatMessageStorePort storePort;
    private final ChatDtoMapper mapper;
    @Override
    public List<ChatMessageDto> getRecent(int size) {
        return storePort.findRecent(size).stream()
                .map(mapper::toMap)
                .toList();
    }
}
