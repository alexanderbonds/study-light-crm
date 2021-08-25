package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.Company;
import ru.lightcrm.entities.Profile;
import ru.lightcrm.utils.CustomDateDeserializer;

import javax.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ApiModel(description = "Класс, представляющий профиль с подробными сведениями о конкретном сотруднике", parent = ProfileDto.class)
@JsonRootName("ProfileFullDto")
public class ProfileFullDto extends ProfileDto {

    @ApiModelProperty(notes = "Пол сотрудника", dataType = "String", example = "М", position = 12)
    @JsonProperty("sex")
    private String sex;

    @ApiModelProperty(notes = "Номер телефона сотрудника", dataType = "String", example = "89998887766", position = 13)
    @JsonProperty("phone")
    private String phone;

    @Email(message = "Некорректный формат электронной почты сотрудника")
    @ApiModelProperty(notes = "Электронная почта сотрудника", dataType = "String", example = "ivan@mail.com", position = 14)
    @JsonProperty("email")
    private String email;

    @Past(message = "Дата рождения сотрудника должна быть раньше настоящего времени")
    @ApiModelProperty(notes = "Дата рождения сотрудника", dataType = "OffsetDateTime", example = "1990-12-25", position = 15)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("birthdate")
    private OffsetDateTime birthdate;

    @ApiModelProperty(notes = "Собственная характеристика сотрудника", dataType = "String", position = 16)
    @JsonProperty("about")
    private String about;

    @PastOrPresent(message = "Дата увольнения должна быть не позже настоящего времени")
    @ApiModelProperty(notes = "Дата увольнения сотрудника", dataType = "OffsetDateTime", example = "2000-12-25", position = 17)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("dismissalDate")
    private OffsetDateTime dismissalDate;

    // Company
    @ApiModelProperty(notes = "Компании, курируемые сотрудником", dataType = "List<CompanyDto>", position = 18)
    @JsonProperty("companyNames")
    private List<String> companyNames;

    // Department
    @ApiModelProperty(notes = "Список отделов, возглавляемых сотрудником", dataType = "List<DepartmentDto>", position = 19)
    @JsonProperty("managedDepartments")
    private List<DepartmentDto> managedDepartments;

    // Project
    @ApiModelProperty(notes = "Проекты, курируемые сотрудником", dataType = "List<ProjectDto>", position = 20)
    @JsonProperty("managedProjects")
    private List<ProjectDto> managedProjects;

    @ApiModelProperty(notes = "Проекты сотрудника", dataType = "List<ProjectDto>", position = 21)
    @JsonProperty("projects")
    private List<ProjectDto> projects;

    // Task
    @ApiModelProperty(notes = "Задачи сотрудника", dataType = "List<TaskDto>", position = 22)
    @JsonProperty("tasks")
    private List<TaskDto> tasks;

    @ApiModelProperty(notes = "Задачи, наблюдаемые сотрудником", dataType = "List<TaskDto>", position = 23)
    @JsonProperty("observedTasks")
    private List<TaskDto> observedTasks;

    // Comment
    @ApiModelProperty(notes = "Комментарии, оставленные сотрудником", dataType = "List<CommentDto>", position = 24)
    @JsonProperty("comments")
    private List<CommentDto> comments;

    public ProfileFullDto(Profile profile) {
        super(profile);
        this.sex = profile.getSex();
        this.phone = profile.getPhone();
        this.email = profile.getEmail();
        this.birthdate = profile.getBirthdate();
        this.about = profile.getAbout();
        this.dismissalDate = profile.getDismissalDate();
        // Company
        //todo мини Dto для компании - companyId + companyName (иначе циклическая ссылка)
        //временно сделан лист названий компаний
        this.companyNames = profile.getCompanies() != null
                ? profile.getCompanies().stream().map(Company::getName).collect(Collectors.toList())
                : new ArrayList<>();
        // Department
        this.managedDepartments = profile.getManagedDepartments() != null
                ? profile.getManagedDepartments().stream().map(DepartmentDto::new).collect(Collectors.toList())
                : Collections.emptyList();
        // Comment
        this.comments = profile.getComments() != null
                ? profile.getComments().stream().map(CommentDto::new).collect(Collectors.toList())
                : Collections.emptyList();
        // Project
        this.managedProjects = profile.getManagedProjects() != null
                ? profile.getManagedProjects().stream().map(ProjectDto::new).collect(Collectors.toList())
                : Collections.emptyList();
        this.projects = profile.getProjects() != null
                ? profile.getProjects().stream().map(ProjectDto::new).collect(Collectors.toList())
                : Collections.emptyList();
        // Task
        this.tasks = profile.getTasks() != null
                ? profile.getTasks().stream().map(TaskDto::new).collect(Collectors.toList())
                : Collections.emptyList();
        this.observedTasks = profile.getObservedTasks() != null
                ? profile.getObservedTasks().stream().map(TaskDto::new).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
