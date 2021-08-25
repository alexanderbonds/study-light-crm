package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "/api/v1/departments", tags = "Контроллер для работы с отделами", produces = "application/json")
@RequestMapping(value = "/api/v1/departments", produces = "application/json")
public interface DepartmentController {

  @ApiOperation(value = "Возвращает список названий всех отделов", httpMethod = "GET", produces = "application/json", response = String.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "List"),
      @ApiResponse(code = 401, message = "Клиент не авторизован"),
      @ApiResponse(code = 403, message = "Нет прав"),
  })
  @GetMapping("/names")
  List<String> getAllDepartmentNames();
}
