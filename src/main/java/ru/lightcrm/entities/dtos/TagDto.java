package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.Tag;


@ApiModel(description = "Тег DTO")
@Data
@NoArgsConstructor
@JsonRootName("TagDto")
public class TagDto {

    @ApiModelProperty(notes = "Идентификатор тега", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование тега", example = "первый", required = true, position = 2)
    @JsonProperty("name")
    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
