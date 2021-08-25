package ru.lightcrm.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.utils.MessageStatus;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "recipient_id")
    private Long recipientId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private OffsetDateTime timestamp;

    @Column(name = "message_status")
    private MessageStatus messageStatus;
}
