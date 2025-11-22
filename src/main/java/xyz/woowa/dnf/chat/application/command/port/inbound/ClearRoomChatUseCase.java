package xyz.woowa.dnf.chat.application.command.port.inbound;

public interface ClearRoomChatUseCase {
    void clear(Command command);

    record Command(String roomId) {
    }
}
