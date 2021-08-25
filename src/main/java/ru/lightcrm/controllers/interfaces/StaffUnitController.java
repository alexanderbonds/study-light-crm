package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import ru.lightcrm.entities.dtos.StaffUnitDto;

import java.util.List;

@Api(value = "/api/v1/staff_units", tags = "Контроллер для работы с должностями", produces = "application/json")
@RequestMapping(value = "/api/v1/staff_units", produces = "application/json")
public interface StaffUnitController {

  @ApiOperation(value = "Возвращает список названий всех должностей", httpMethod = "GET", produces = "application/json", response = String.class, responseContainer = "List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "List"),
      @ApiResponse(code = 401, message = "Клиент не авторизован"),
      @ApiResponse(code = 403, message = "Нет прав"),
  })
  @GetMapping("/names")
  List<String> getAllStaffUnitNames();

  @ApiOperation(value = "Сохранить новую должность",
          httpMethod = "POST",
          consumes = "application/json",
          produces = "application/json",
          response = StaffUnitDto.class
  )
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = StaffUnitDto.class),
          @ApiResponse(code = 201, message = "Новая должность успешно создана", response = StaffUnitDto.class),
          @ApiResponse(code = 400, message = "Некорректное тело запроса"),
          @ApiResponse(code = 401, message = "Клиент не авторизован"),
          @ApiResponse(code = 403, message = "Нет прав"),
          @ApiResponse(code = 404, message = "Ресурс отсутствует")
  })
  @PostMapping(consumes = "application/json", produces = "application/json")
  StaffUnitDto saveStaffUnit(@ApiParam(value = "JSON представление данных новой должности", required = true) @RequestBody StaffUnitDto staffUnitDto);

  @ApiOperation(value = "Обновить должность",
          httpMethod = "PUT",
          consumes = "application/json",
          produces = "application/json",
          response = StaffUnitDto.class
  )
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = StaffUnitDto.class),
          @ApiResponse(code = 201, message = "Должность успешно изменена", response = StaffUnitDto.class),
          @ApiResponse(code = 400, message = "Некорректное тело запроса"),
          @ApiResponse(code = 401, message = "Клиент не авторизован"),
          @ApiResponse(code = 403, message = "Нет прав"),
          @ApiResponse(code = 404, message = "Ресурс отсутствует")
  })
  @PutMapping(consumes = "application/json", produces = "application/json")
  StaffUnitDto updateStuffUnit(@ApiParam(value = "JSON представление данных изменяемой должности", required = true) @RequestBody StaffUnitDto staffUnitDto);

  @ApiOperation(value = "Удалить должность",
          httpMethod = "DELETE")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Некорректное тело запроса"),
          @ApiResponse(code = 401, message = "Клиент не авторизован"),
          @ApiResponse(code = 403, message = "Нет прав"),
          @ApiResponse(code = 404, message = "Ресурс отсутствует")
  })
  @DeleteMapping(value = "/{id}")
  void deleteStaffUnitById(@ApiParam(value = "идентификатор удаляемой должности", required = true, example = "1") @PathVariable Long id);

}
