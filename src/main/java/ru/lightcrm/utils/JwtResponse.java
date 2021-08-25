package ru.lightcrm.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Объкт ответа на успешную авторизацию авторизацию")
public class JwtResponse {

  @ApiModelProperty(
      notes = "Токен авторизации",
      example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiO…DEzfQ.7SusOF1VG57swkW96Zg8Mt8fTTxP2Ra2m0LM",
      required = true
  )
  private String token;

  public JwtResponse(String token) {
    this.token = token;
  }
}
