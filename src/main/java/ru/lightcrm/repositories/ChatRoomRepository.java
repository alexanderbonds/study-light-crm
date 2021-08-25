package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lightcrm.entities.ChatRoom;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(@NotNull Long senderId, @NotNull Long recipientId);
    List<ChatRoom> findAllBySenderId(Long senderId);
    Optional<ChatRoom> findByChatIdAndRecipientId(@NotNull String chatId, @NotNull Long recipientId);
}
