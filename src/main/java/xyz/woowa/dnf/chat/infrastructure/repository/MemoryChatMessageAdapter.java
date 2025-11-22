package xyz.woowa.dnf.chat.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.domain.ChatMessage;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Profile("memory")
public class MemoryChatMessageAdapter implements ChatMessageStorePort {
    private final Map<String, List<ChatMessage>> messagesByRoom = new HashMap<>();

    @Override
    public ChatMessage save(ChatMessage message) {
        messagesByRoom.computeIfAbsent(message.getRoomId(), (key) -> new ArrayList<>())
                .add(message);
        return message;
    }

    @Override
    public List<ChatMessage> findRecent(String roomId, int size) {
        var list = messagesByRoom.getOrDefault(roomId, List.of());
        int fromIndex = Math.max(0, list.size() - size);
        List<ChatMessage> result = new ArrayList<>(list.subList(fromIndex, list.size()));
        Collections.reverse(result);
        return result;
    }
}
