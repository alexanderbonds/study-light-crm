package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightcrm.entities.StaffUnit;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Штатная единица DTO")
@Data
@NoArgsConstructor
@JsonRootName("StaffUnitDto")
public class StaffUnitDto {

    @ApiModelProperty(notes = "Идентификатор штатной единицы", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Наименование штатной единицы", example = "Менеджер", required = true, position = 2)
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Роли штатной единицы", example = "(Administrator, Manager, User)", required = true, position = 3)
    @JsonProperty("roles")
    private Set<RoleDto> roles;

    public StaffUnitDto(StaffUnit staffUnit) {
        this.id = staffUnit.getId();
        this.name = staffUnit.getName();
        this.roles = staffUnit.getRoles() != null
                ? staffUnit.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
