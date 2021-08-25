package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.TaskState;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Класс Статус состояния задачи.")
@JsonRootName("TaskStateDto")
public class TaskStateDto {

    @ApiModelProperty(notes = "Уникальный идентификатор статуса.", example = "1", required = true, position = 0)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование статуса.", example = "Завершена.", required = true, position = 1)
    @JsonProperty("name")
    private String name;

    public TaskStateDto(TaskState taskState){
        this.id = taskState.getId();
        this.name = taskState.getName();
    }
}
