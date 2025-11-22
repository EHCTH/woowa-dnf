package xyz.woowa.dnf.chat.application.command.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.woowa.dnf.chat.application.command.port.inbound.ClearRoomChatUseCase;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;

@Service
@RequiredArgsConstructor
public class ClearRoomChatService implements ClearRoomChatUseCase {
    private final ChatMessageStorePort storePort;

    @Override
    @Transactional
    public void clear(Command command) {
        storePort.clear(command.roomId());

    }
}
