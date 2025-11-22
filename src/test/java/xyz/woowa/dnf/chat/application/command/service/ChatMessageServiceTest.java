package xyz.woowa.dnf.chat.application.command.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.chat.application.assembler.ChatDtoMapper;
import xyz.woowa.dnf.chat.application.command.port.inbound.ClearRoomChatUseCase;
import xyz.woowa.dnf.chat.application.command.port.inbound.SendChatMessageUseCase;
import xyz.woowa.dnf.chat.application.command.port.inbound.dto.ChatMessageDto;
import xyz.woowa.dnf.chat.application.port.outbound.ChatMessageStorePort;
import xyz.woowa.dnf.chat.application.query.port.inbound.GetChatMessageUseCase;
import xyz.woowa.dnf.chat.application.query.port.service.GetChatMessageService;
import xyz.woowa.dnf.chat.infrastructure.repository.MemoryChatMessageAdapter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ChatMessageServiceTest {
    private ChatDtoMapper mapper;
    private ChatMessageStorePort memoryStorePort;
    private SendChatMessageUseCase chatMessageService;
    private GetChatMessageUseCase getChatMessageUseCase;
    private ClearRoomChatUseCase clearRoomChatUseCase;

    @BeforeEach
    void init() {
        mapper = new ChatDtoMapper();
        memoryStorePort = new MemoryChatMessageAdapter();
        chatMessageService = new ChatMessageService(memoryStorePort, mapper);
        getChatMessageUseCase = new GetChatMessageService(memoryStorePort, mapper);
        clearRoomChatUseCase= new ClearRoomChatService(memoryStorePort);
    }

    @Test
    @DisplayName("저장 및 조회 테스트")
    void 저장_및_조회_테스트() {
        // give
        SendChatMessageUseCase.Command command1 = new SendChatMessageUseCase.Command(
                "1", "writer1", "content1");
        SendChatMessageUseCase.Command command2 = new SendChatMessageUseCase.Command(
                "1", "writer2", "content2");
        SendChatMessageUseCase.Command command3 = new SendChatMessageUseCase.Command(
                "1", "writer3", "content3");


        // when
        chatMessageService.send(command1);
        chatMessageService.send(command2);
        ChatMessageDto send = chatMessageService.send(command3);
        List<ChatMessageDto> recent = getChatMessageUseCase.getRecent("1", 10);

        // then
        assertThat(send.roomId()).isEqualTo("1");
        assertThat(send.writer()).isEqualTo("writer3");
        assertThat(send.content()).isEqualTo("content3");

        assertThat(recent).hasSize(3);
        assertThat(recent.getFirst()).isEqualTo(send);
    }

    @Test
    @DisplayName("삭제 테스트")
    void 삭제_테스트() {
        // give
        SendChatMessageUseCase.Command command1 = new SendChatMessageUseCase.Command(
                "1", "writer1", "content1");
        SendChatMessageUseCase.Command command2 = new SendChatMessageUseCase.Command(
                "1", "writer2", "content2");
        SendChatMessageUseCase.Command command3 = new SendChatMessageUseCase.Command(
                "1", "writer3", "content3");


        // when
        chatMessageService.send(command1);
        chatMessageService.send(command2);
        chatMessageService.send(command3);

        clearRoomChatUseCase.clear(new ClearRoomChatUseCase.Command("1"));

        List<ChatMessageDto> recent = getChatMessageUseCase.getRecent("1", 10);

        // then
        assertThat(recent).hasSize(0);
    }


}
