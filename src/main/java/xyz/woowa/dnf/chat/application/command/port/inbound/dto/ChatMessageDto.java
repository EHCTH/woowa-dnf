package xyz.woowa.dnf.chat.application.command.port.inbound.dto;

import java.time.LocalDateTime;

public record ChatMessageDto(Long id,
                             String writer,
                             String content,
                             LocalDateTime createdAt) {
}
