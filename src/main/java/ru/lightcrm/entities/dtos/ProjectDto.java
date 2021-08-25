package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.Project;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Класс Проект DTO для группировки задач одного бизнес-процесса или направления.")
@JsonRootName("ProjectDto")
public class ProjectDto {

    @ApiModelProperty(notes = "Уникальный идентификатор проекта.", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование проекта.", example = "Ревизия складских остатков.", required = true, position = 2)
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Описание проекта.", example = "Плановая ежегодная ревизия складских остатков.", required = true, position = 3)
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(notes = "Идентификатор руководителя проекта.", required = true, position = 4)
    @JsonProperty("manager")
    private ProfileDto manager;

    @ApiModelProperty(notes = "Список ответственных за проект.", required = true, position = 5)
    @JsonProperty("profiles")
    private Set<ProfileDto> profiles;

    @ApiModelProperty(notes = "Список заданий проекта.", required = true, position = 6)
    @JsonProperty("tasks")
    private Set<TaskDto> tasks;

    @ApiModelProperty(notes = "Идентификатор связанного с проектом клиента.", required = true, position = 4)
    @JsonProperty("companyId")
    private Long companyId;

    @ApiModelProperty(notes = "Наименование (имя) связанного с проектом клиента.", required = true, position = 5)
    @JsonProperty("companyName")
    private String companyName;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.manager = new ProfileDto(project.getManager());
        this.profiles = project.getProfiles() != null
                ? project.getProfiles().stream().map(ProfileDto::new).collect(Collectors.toSet())
                : null;
        this.tasks = project.getTasks() != null
                ? project.getTasks().stream().map(TaskDto::new).collect(Collectors.toSet())
                : null;
    }
}
