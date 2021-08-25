package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.ChatRoom;

@ApiModel(description = "Чат")
@Data
@NoArgsConstructor
@JsonRootName("ChatRoomDto")
public class ChatRoomDto {

    @ApiModelProperty(notes = "Идентификатор чата", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Идентификатор комнаты", example = "1_2", required = true, position = 2)
    @JsonProperty("chatId")
    private String chatId;

    @ApiModelProperty(notes = "Идентификатор отправителя", example = "1", required = true, position = 3)
    @JsonProperty("senderId")
    private Long senderId;

    @ApiModelProperty(notes = "Идентификатор получателя", example = "1", required = true, position = 4)
    @JsonProperty("recipientId")
    private Long recipientId;

    @ApiModelProperty(notes = "Имя получателя", example = "Петр Пертов", position = 5)
    @JsonProperty("recipientName")
    private String recipientName;

    @ApiModelProperty(notes = "Последнее сообщение в чате", position = 6)
    @JsonProperty("lastMessage")
    private ChatMessageDto lastMessage;

    @ApiModelProperty(notes = "Количество непрочитанных сообщений", position = 7)
    @JsonProperty("unreadMessageCount")
    private Long unreadMessageCount;

    public ChatRoomDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.chatId = chatRoom.getChatId();
        this.senderId = chatRoom.getSenderId();
        this.recipientId = chatRoom.getRecipientId();
    }
}
