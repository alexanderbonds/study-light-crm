package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.ChatMessage;
import ru.lightcrm.entities.dtos.ChatMessageDto;
import ru.lightcrm.utils.MessageStatus;

import java.util.List;

public interface ChatMessageService {
    ChatMessageDto save(ChatMessageDto chatMessage);
    Long countNewMessages(Long senderId, Long recipientId);
    List<ChatMessageDto> findChatMessages(Long senderId, Long recipientId);
    ChatMessageDto findById(Long id);
    void updateStatuses(List<ChatMessage> messages, Long senderId, Long recipientId, MessageStatus status);
}
