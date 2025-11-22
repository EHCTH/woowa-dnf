package xyz.woowa.dnf.chat.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.woowa.dnf.chat.domain.ChatMessage;


public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
    @Query("select m from ChatMessage m " + "where m.roomId = :roomId " + "order by m.createdAt desc")
    Page<ChatMessage> findByRoomIdOrderByCreatedAtDesc(@Param("roomId") String roomId, Pageable pageable);
    void deleteByRoomId(String roomId);
}
