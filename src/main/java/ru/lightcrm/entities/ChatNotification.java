package ru.lightcrm.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private Long messageId;
    private String chatId;
    private Long senderId;
    private String senderName;
}
