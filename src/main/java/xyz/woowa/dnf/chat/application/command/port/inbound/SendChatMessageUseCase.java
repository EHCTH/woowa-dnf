package xyz.woowa.dnf.chat.application.command.port.inbound;


import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;

public interface SendChatMessageUseCase {
    ChatMessageDto send(Command command);

    record Command(String roomId, String writer, String content) {

    }
}
