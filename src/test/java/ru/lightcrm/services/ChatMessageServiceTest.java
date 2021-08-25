package ru.lightcrm.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.ChatMessage;
import ru.lightcrm.entities.ChatRoom;
import ru.lightcrm.entities.dtos.ChatMessageDto;
import ru.lightcrm.repositories.ChatMessageRepository;
import ru.lightcrm.repositories.ChatRoomRepository;
import ru.lightcrm.services.interfaces.ChatMessageService;
import ru.lightcrm.utils.MessageStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ChatMessageServiceTest {
    private static final Long SENDER_ID = 1L;
    private static final Long RECIPIENT_ID = 2L;
    private static final String CHAT_ID = "1_2";

    @Autowired
    private ChatMessageService chatMessageService;

    @MockBean
    private ChatMessageRepository chatMessageRepository;
    @MockBean
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void countNewMessagesTest() {
        Mockito.doReturn(2L).when(chatMessageRepository)
                .countChatMessageBySenderIdAndRecipientIdAndMessageStatus(RECIPIENT_ID, SENDER_ID, MessageStatus.RECEIVED);

        Long count = chatMessageService.countNewMessages(SENDER_ID, RECIPIENT_ID);
        Assertions.assertEquals(2L, count);
    }

    @Test
    public void findChatMessagesTest() {
        Mockito.doReturn(generateMockMessages()).when(chatMessageRepository)
                .findByChatId(CHAT_ID);

        Mockito.doReturn(generateMockChatRoom()).when(chatRoomRepository)
                .findBySenderIdAndRecipientId(SENDER_ID, RECIPIENT_ID);

        List<ChatMessageDto> messages = chatMessageService.findChatMessages(SENDER_ID, RECIPIENT_ID);
        Assertions.assertEquals(2, messages.size());
        Assertions.assertNotNull(messages);

    }

    private List<ChatMessage> generateMockMessages() {
        return List.of(
                ChatMessage.builder()
                        .id(1L)
                        .chatId(CHAT_ID)
                        .senderId(SENDER_ID)
                        .recipientId(RECIPIENT_ID)
                        .senderName("Виктор Викторов")
                        .recipientName("Петр Петров")
                        .content("Превед, кросафчег!")
                        .timestamp(OffsetDateTime.now())
                        .messageStatus(MessageStatus.RECEIVED)
                        .build(),
                ChatMessage.builder()
                        .id(1L)
                        .chatId(CHAT_ID)
                        .senderId(RECIPIENT_ID)
                        .recipientId(SENDER_ID)
                        .senderName("Петр Петров")
                        .recipientName("Виктор Викторов")
                        .content("Превед, медвед!")
                        .timestamp(OffsetDateTime.now())
                        .messageStatus(MessageStatus.RECEIVED)
                        .build());
    }

    private Optional<ChatRoom> generateMockChatRoom() {
        return Optional.of(ChatRoom.builder()
                .id(1L)
                .chatId(CHAT_ID)
                .senderId(SENDER_ID)
                .recipientId(RECIPIENT_ID)
                .build());
    }
}
