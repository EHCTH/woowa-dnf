package xyz.woowa.dnf.chat.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.woowa.dnf.chat.domain.ChatMessage;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop50ByOrderByCreatedAtDesc();
}
