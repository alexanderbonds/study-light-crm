package ru.lightcrm.entities.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.lightcrm.entities.User;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Пользователь DTO")
@Data
@JsonRootName("UserDto")
public class UserDto {

    @ApiModelProperty(notes = "Идентификатор пользователя", example = "1", required = true, position = 1)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(notes = "Логин пользователя", example = "User", required = true, position = 2)
    @JsonProperty("login")
    private String login;

    @ApiModelProperty(notes = "Пароль пользователя", example = "123", required = true, position = 3)
    @JsonProperty("password")
    private String password;

    @ApiModelProperty(notes = "Активность пользователя", example = "True", required = true, position = 4)
    @JsonProperty("enabled")
    private boolean enabled;

    @ApiModelProperty(notes = "Права пользователя", example = "(Изменение задач, Удаление задач)", required = true, position = 5)
    @JsonProperty("priorities")
    private Set<PriorityDto> priorities;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.priorities = user.getPriorities() != null
                ? user.getPriorities().stream().map(PriorityDto::new).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
