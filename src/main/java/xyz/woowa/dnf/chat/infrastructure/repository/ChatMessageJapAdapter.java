package xyz.woowa.dnf.chat.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.domain.ChatMessage;
import xyz.woowa.dnf.chat.infrastructure.persistence.ChatMessageJpaRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Profile("mysql")
public class ChatMessageJapAdapter implements ChatMessageStorePort {
    private final ChatMessageJpaRepository repository;

    @Override
    public ChatMessage save(ChatMessage message) {
        return repository.save(message);
    }

    @Override
    public List<ChatMessage> findRecent(int size) {
        return repository.findTop50ByOrderByCreatedAtDesc();
    }
}
