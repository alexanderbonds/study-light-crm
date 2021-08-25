package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.ChatMessage;
import ru.lightcrm.entities.dtos.ChatMessageDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.ChatMessageRepository;
import ru.lightcrm.services.interfaces.ChatMessageService;
import ru.lightcrm.services.interfaces.ChatRoomService;
import ru.lightcrm.utils.MessageStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public ChatMessageDto save(ChatMessageDto chatMessageDto) {
        chatMessageDto.setMessageStatus(MessageStatus.RECEIVED);
        ChatMessage saved = ChatMessage.builder()
                .id(chatMessageDto.getId())
                .chatId(chatMessageDto.getChatId())
                .senderId(chatMessageDto.getSenderId())
                .recipientId(chatMessageDto.getRecipientId())
                .senderName(chatMessageDto.getSenderName())
                .recipientName(chatMessageDto.getRecipientName())
                .content(chatMessageDto.getContent())
                .timestamp(chatMessageDto.getTimestamp())
                .messageStatus(chatMessageDto.getMessageStatus())
                .build();
        return new ChatMessageDto(chatMessageRepository.save(saved));
    }

    @Override
    public Long countNewMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.countChatMessageBySenderIdAndRecipientIdAndMessageStatus(recipientId, senderId, MessageStatus.RECEIVED);
    }

    @Override
    public List<ChatMessageDto> findChatMessages(Long senderId, Long recipientId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);
        List<ChatMessage> messages = chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
        if (messages.size() > 0) {
            updateStatuses(messages, senderId, recipientId, MessageStatus.DELIVERED);
        }
        return messages.stream().map(ChatMessageDto::new).collect(Collectors.toList());
    }

    @Override
    public ChatMessageDto findById(Long id) {
        return chatMessageRepository.findById(id).map(chatMessage -> {
            chatMessage.setMessageStatus(MessageStatus.DELIVERED);
            return chatMessageRepository.save(chatMessage);
        }).map(ChatMessageDto::new).orElseThrow(() -> new ResourceNotFoundException(String.format("Сообщение с id %d не найдено", id)));
    }

    @Override
    public void updateStatuses(List<ChatMessage> messages, Long senderId, Long recipientId, MessageStatus status) {
        for (ChatMessage message : messages) {
            if (recipientId.equals(message.getSenderId())
                    && senderId.equals(message.getRecipientId())
                    && message.getMessageStatus() != status) {
                message.setMessageStatus(status);
                chatMessageRepository.save(message);
            }
        }
    }
}
