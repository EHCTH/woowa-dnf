package xyz.woowa.dnf.chat.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.domain.ChatMessage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Profile("memory")
public class MemoryChatMessageAdapter implements ChatMessageStorePort {
    private final HashMap<Long, ChatMessage> store = new HashMap<>();
    private static final Comparator<ChatMessage> CREATED_AT = Comparator.comparing(ChatMessage::getCreatedAt);
    private Long seq = 0L;

    @Override
    public ChatMessage save(ChatMessage message) {
        long id = seq++;
        ChatMessage saved = ChatMessage.create(message.getWriter(), message.getContent());
        store.put(id, saved);
        return saved;
    }

    @Override
    public List<ChatMessage> findRecent(int size) {
        return store.values().stream()
                .sorted(CREATED_AT.reversed())
                .limit(50)
                .sorted(CREATED_AT)
                .toList();
    }
}
