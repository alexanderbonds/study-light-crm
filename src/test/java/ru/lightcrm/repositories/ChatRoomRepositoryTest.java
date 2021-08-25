package ru.lightcrm.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.lightcrm.entities.ChatRoom;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ChatRoomRepositoryTest {
    private static final Long SENDER_ID = 1L;
    private static final Long RECIPIENT_ID = 2L;
    private static final String CHAT_ID = "1_2";

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void findBySenderIdAndRecipientIdTest() {
        ChatRoom chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(SENDER_ID, RECIPIENT_ID).orElse(null);

        Assertions.assertNotNull(chatRoom);
        Assertions.assertEquals(CHAT_ID, chatRoom.getChatId());
    }

    @Test
    public void findAllBySenderIdTest() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllBySenderId(SENDER_ID);

        Assertions.assertNotNull(chatRooms);
        Assertions.assertEquals(2, chatRooms.size());
        for (ChatRoom chatRoom : chatRooms) {
            Assertions.assertEquals(SENDER_ID, chatRoom.getSenderId());
        }
    }

    @Test
    public void findByChatIdAndRecipientIdTest() {
        ChatRoom chatRoom = chatRoomRepository.findByChatIdAndRecipientId(CHAT_ID, RECIPIENT_ID).orElse(null);

        Assertions.assertNotNull(chatRoom);
        Assertions.assertEquals(SENDER_ID, chatRoom.getSenderId());
    }
}
