package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.Comment;
import ru.lightcrm.utils.CustomDateDeserializer;

import java.time.OffsetDateTime;

@ApiModel(description = "Комментарии dto в приложении")
@Data
@NoArgsConstructor
@JsonRootName("CommentDto")
public class CommentDto {

    @ApiModelProperty(notes = "Идентификатор комментария", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Имя автора комментария", example = "Иван", required = true, position = 2)
    @JsonProperty("authorFirstName")
    private String authorFirstName;

    @ApiModelProperty(notes = "Фамилия автора комментария", example = "Иванов", required = true, position = 3)
    @JsonProperty("authorLastName")
    private String authorLastName;

    @ApiModelProperty(notes = "Почта автора комментария", example = "ivan@gmail.com", required = true, position = 4)
    @JsonProperty("authorEmail")
    private String authorEmail;

    @ApiModelProperty(notes = "Дата создания комментария", required = true, position = 5)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("createdDate")
    private OffsetDateTime createdDate;

    @ApiModelProperty(notes = "Содержание комментария", example = "Уточнить реквизиты компании.", required = true, position = 6)
    @JsonProperty("text")
    private String text;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.authorFirstName = comment.getAuthor().getFirstname();
        this.authorLastName = comment.getAuthor().getLastname();
        this.authorEmail = comment.getAuthor().getEmail();
        this.createdDate = comment.getCreatedDate().withNano(0);
        this.text = comment.getText();
    }
}
