package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.Department;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Класс Отдел DTO для организации иерархической структуры компании.")
@JsonRootName("DepartmentDto")
public class DepartmentDto {

    @ApiModelProperty(notes = "Уникальный идентификатор отдела.", example = "1", required = true, position = 0)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование отдела.", example = "Отдел Информационных Технологий.", required = true, position = 1)
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Описание деятельности отдела.", example = "Техническая поддержка сотрудников компании.", required = true, position = 2)
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(notes = "Идентификатор руководителя отдела.", required = true, position = 3)
    @JsonProperty("leaderId")
    private Long leaderId;

    @ApiModelProperty(notes = "Идентификатор отдела, которому подчиняется.", required = true, position = 4)
    @JsonProperty("leadDepartmentId")
    private Long leadDepartmentId;

    public DepartmentDto(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.description = department.getDescription();
        this.leaderId = department.getLeader().getId();
        this.leadDepartmentId = department.getLeadDepartment() != null
                ? department.getLeadDepartment().getId()
                : null;
    }
}
