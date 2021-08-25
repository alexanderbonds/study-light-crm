package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.lightcrm.entities.Role;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Роль DTO")
@Data
@JsonRootName("RoleDto")
public class RoleDto {

    @ApiModelProperty(notes = "Идентификатор роли", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование роли", example = "ROLE_ADMIN", required = true, position = 2)
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Наименование роли для отображения", example = "Администратор", required = true, position = 3)
    @JsonProperty("visibleName")
    private String visibleName;

    @ApiModelProperty(notes = "Права, принадлежащие данной роли", example = "(Изменение задач, Удаление задач)", required = true, position = 4)
    @JsonProperty("priorities")
    private Set<PriorityDto> priorities;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.visibleName = role.getVisibleName();
        this.priorities = role.getPriorities() != null
                ? role.getPriorities().stream().map(PriorityDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
