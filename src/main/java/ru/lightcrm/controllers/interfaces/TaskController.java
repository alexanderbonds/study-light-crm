package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.lightcrm.entities.dtos.TaskDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Api(value = "/api/v1/tasks", tags = "Контроллер для работы с задачами", produces = "application/json")
@RequestMapping(value = "/api/v1/tasks", produces = "application/json")
public interface TaskController {

    @ApiOperation(value = "Получить список задач",
            httpMethod = "GET",
            produces = "application/json",
            response = TaskDto.class,
            responseContainer = "List"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = TaskDto.class, responseContainer = "List"),
                    @ApiResponse(code = 401, message = "Клиент не авторизован"),
                    @ApiResponse(code = 403, message = "Нет прав"),
                    @ApiResponse(code = 404, message = "Ресурс отсутствует")
            }
    )
    @GetMapping
    List<TaskDto> getAllTasks(@ApiParam(value = "Список иденктификаторов статусов", required = false) @RequestParam(required = false) List<Long> taskStatesId,
            @ApiParam(value = "Map с набором параметров и значений",  required = false, example = "responsibleId: 1") @RequestParam(required = false) Map<String, String> params);

    @ApiOperation(value = "Получить задачу по её Id",
            httpMethod = "GET",
            produces = "application/json",
            response = TaskDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Указан некорректный id"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Задача с указанным id отсутствует")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    TaskDto getTaskById(@ApiParam(value = "идентификатор задачи",  required = true, example = "1") @PathVariable Long id);

    @ApiOperation(value = "Сохранить новую задачу (не работает)",
            httpMethod = "POST",
            consumes = "application/json",
            produces = "application/json",
            response = TaskDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TaskDto.class),
            @ApiResponse(code = 201, message = "Новая задача успешно создана", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    TaskDto saveTask(@ApiParam(value = "JSON представление данных новой задачи", required = true) @RequestBody TaskDto taskDTO);

    @ApiOperation(value = "Обновить задачу (не работает)",
            httpMethod = "PUT",
            consumes = "application/json",
            produces = "application/json",
            response = TaskDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TaskDto.class),
            @ApiResponse(code = 201, message = "Задача успешно изменена", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @PutMapping(consumes = "application/json", produces = "application/json")
    TaskDto updateTask(@ApiParam(value = "JSON представление данных измененной задачи", required = true) @RequestBody TaskDto taskDTO);

    @ApiOperation(value = "Удалить задачу (не работает)",
            httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @DeleteMapping(value = "/{id}")
    void deleteTaskById(@ApiParam(value = "идентификатор удаляемой задачи", required = true, example = "1") @PathVariable Long id);

}
