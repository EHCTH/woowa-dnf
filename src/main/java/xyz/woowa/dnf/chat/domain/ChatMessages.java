package xyz.woowa.dnf.chat.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessages {
    private final List<ChatMessage> chatMessages;

    public static ChatMessages empty() {
        return new ChatMessages(new ArrayList<>());
    }

    public void add(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }

    public List<ChatMessage> recent(int size) {
        int fromIndex = Math.max(0, chatMessages.size() - size);
        List<ChatMessage> result = new ArrayList<>(chatMessages.subList(fromIndex, chatMessages.size()));
        Collections.reverse(result);
        return List.copyOf(result);
    }

    public void clear() {
        chatMessages.clear();
    }

    public int size() {
        return chatMessages.size();
    }

    public boolean isEmpty() {
        return chatMessages.isEmpty();
    }
}
