package xyz.woowa.dnf.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false, length = 100)
    private String roomId;

    @Column(nullable = false, length = 30)
    private String writer;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static ChatMessage create(String roomId, String writer, String content) {
        return new ChatMessage(roomId, writer, content, LocalDateTime.now());
    }

    private ChatMessage(String roomId, String writer, String content, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }

}
