package ru.lightcrm.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Объект запроса на авторизацию")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

  @ApiModelProperty(notes = "Логин пользователя", example = "Harry", required = true, position = 1)
  private String username;

  @ApiModelProperty(notes = "Пароль пользователя", example = "fldsLflsd78ldsp9fl", required = true, position = 1)
  private String password;
}
