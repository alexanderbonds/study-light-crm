package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.dtos.ChatRoomDto;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatId(Long senderId, Long recipientId, boolean createIfNotExist);
    List<ChatRoomDto> getChatsDto(Long senderId);
    ChatRoomDto getChatByIdAndRecipientId(String chatId, Long recipientId);
}
