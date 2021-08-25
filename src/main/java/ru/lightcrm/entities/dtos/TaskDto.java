package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.Profile;
import ru.lightcrm.entities.Task;
import ru.lightcrm.utils.CustomDateDeserializer;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Класс Задача.")
@JsonRootName("TaskDto")
public class TaskDto {

    @ApiModelProperty(notes = "Уникальный идентификатор задачи.", example = "1", required = true)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование задачи.", example = "Отпуск сотрудника.", required = true, position = 1)
    @JsonProperty("title")
    private String title;

    @ApiModelProperty(notes = "Описание задачи.", example = "Соответствующим службам выполнить документальное оформление отпуска.", required = true, position = 2)
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(notes = "Идентификатор постановщика задачи.", required = true, position = 3)
    @JsonProperty("producerId")
    private Long producerId;

    @ApiModelProperty(notes = "Идентификатор ответственного за выполнение (исполнитель) задачи.", required = true, position = 4)
    @JsonProperty("responsibleId")
    private Long responsibleId;

    @ApiModelProperty(notes = "Дата постановки задачи.", required = true, position = 5)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("startDate")
    private OffsetDateTime startDate;

    @ApiModelProperty(notes = "Дата окончания задачи.", required = true, position = 6)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("endDate")
    private OffsetDateTime endDate;

    @ApiModelProperty(notes = "Дата планируемого окончания задачи.", required = true, position = 7)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("deadline")
    private OffsetDateTime deadline;

    @ApiModelProperty(notes = "Статус состояния задачи.", required = true, position = 8)
    @JsonProperty("taskStateId")
    private Long taskStateId;

    @ApiModelProperty(notes = "Признак доступа ответственного к смене даты планируемого окончания задачи.", required = true, position = 9)
    @JsonProperty("allowChangeDeadline")
    private boolean allowChangeDeadline;

    @ApiModelProperty(notes = "Проект к которому относится задача.", required = true, position = 10)
    @JsonProperty("projectId")
    private Long projectId;

    @ApiModelProperty(notes = "Просрочена ли задача", required = true, position = 11)
    @JsonProperty("expired")
    private boolean expired;

    @ApiModelProperty(notes = "Список соисполнителей задачи", required = true, position = 12)
    @JsonProperty("coExecutors")
    private Set<ProfileDto> coExecutors;

    @ApiModelProperty(notes = "Список наблюдателей задачи", required = true, position = 13)
    @JsonProperty("spectators")
    private Set<ProfileDto> spectators;

    @ApiModelProperty(notes = "Список комментариев к заданию", required = true, position = 14)
    @JsonProperty("comments")
    private Set<CommentDto> comments;

    @ApiModelProperty(notes = "Постановщик задачи ", required = false)
    @JsonProperty("producer")
    private ProfileMiniDto producer;

    @ApiModelProperty(notes = "Ответственный за выполнение (исполнитель) задачи", required = false)
    @JsonProperty("responsible")
    private ProfileMiniDto responsible;

    @ApiModelProperty(notes = "Наименование статуса задачи", required = false)
    @JsonProperty("taskStateName")
    private String taskStateName;

    @ApiModelProperty(notes = "Идентификатор связанной с задачей компании.", required = false)
    @JsonProperty("companyId")
    private Long companyId;

    @ApiModelProperty(notes = "Наименование (имя) связанной с задачей компании.", required = false)
    @JsonProperty("companyName")
    private String companyName;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.producerId = task.getProducer().getId();
        this.producer = new ProfileMiniDto(task.getProducer());
        this.responsibleId = task.getResponsible().getId();
        this.responsible = new ProfileMiniDto(task.getResponsible());
        this.startDate = task.getStartDate();
        this.endDate = task.getEndDate();
        this.deadline = task.getDeadline();
        this.taskStateId = task.getTaskState().getId();
        this.taskStateName = task.getTaskState().getName();
        this.allowChangeDeadline = task.isAllowChangeDeadline();
        this.projectId = task.getProject() != null
                ? task.getProject().getId()
                : null;
        this.expired = task.isExpired();
        this.coExecutors = task.getCoExecutors() != null
                ? task.getCoExecutors().stream().map(ProfileDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.spectators = task.getSpectators() != null
                ? task.getSpectators().stream().map(ProfileDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.comments = task.getComments() != null
                ? task.getComments().stream().map(CommentDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
        this.companyId = task.getCompany() != null ? task.getCompany().getId() : null;
        this.companyName = task.getCompany() != null ? task.getCompany().getName() : null;
    }
}
