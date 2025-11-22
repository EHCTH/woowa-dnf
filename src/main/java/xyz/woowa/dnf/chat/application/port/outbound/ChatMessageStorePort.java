package xyz.woowa.dnf.chat.application.port.outbound;

import xyz.woowa.dnf.chat.domain.ChatMessage;

import java.util.List;

public interface ChatMessageStorePort {
    ChatMessage save(ChatMessage message);

    List<ChatMessage> findRecent(int size);
}
