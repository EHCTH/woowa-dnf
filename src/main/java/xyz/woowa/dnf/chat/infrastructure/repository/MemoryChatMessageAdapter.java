package xyz.woowa.dnf.chat.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.domain.ChatMessage;
import xyz.woowa.dnf.chat.domain.ChatMessages;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Profile("memory")
public class MemoryChatMessageAdapter implements ChatMessageStorePort {
    private final Map<String, ChatMessages> messagesByRoom = new HashMap<>();

    @Override
    public ChatMessage save(ChatMessage message) {
        messagesByRoom.computeIfAbsent(message.getRoomId(), (key) -> ChatMessages.empty())
                .add(message);
        return message;
    }

    @Override
    public List<ChatMessage> findRecent(String roomId, int size) {
        ChatMessages messages = messagesByRoom.getOrDefault(roomId, ChatMessages.empty());
        return messages.recent(size);
    }

    @Override
    public void clear(String roomId) {
        ChatMessages chatMessages = messagesByRoom.get(roomId);
        if (chatMessages != null) {
            chatMessages.clear();
        }
    }
}
