package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.lightcrm.entities.Priority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Права DTO")
@Data
@JsonRootName("PriorityDto")
public class PriorityDto {

    @ApiModelProperty(notes = "Идентификатор права", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование права", example = "CHANGE_TASK", required = true, position = 2)
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Наименование права для отображения", example = "Изменение задачи", required = true, position = 3)
    @JsonProperty("visibleName")
    private String visibleName;

    @ApiModelProperty(notes = "Роли, в которые включено данное право", example = "(ROLE_ADMIN, ROLE_MANAGER)", required = true, position = 4)
    @JsonProperty("roles")
    private Set<RoleDto> roles;

    public PriorityDto(Priority priority) {
        this.id = priority.getId();
        this.name = priority.getName();
        this.visibleName = priority.getVisibleName();
        this.roles = priority.getRoles() != null
                ? priority.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
