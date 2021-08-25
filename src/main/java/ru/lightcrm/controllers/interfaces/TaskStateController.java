package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lightcrm.entities.dtos.TaskDto;
import ru.lightcrm.entities.dtos.TaskStateDto;

import java.util.List;

@Api(value = "/api/v1/taskstates", tags = "Контроллер для работы со статусами задач", produces = "application/json")
@RequestMapping(value = "/api/v1/taskstates", produces = "application/json")
public interface TaskStateController {

    @ApiOperation(value = "Получить список статусов задач",
            httpMethod = "GET",
            produces = "application/json",
            response = TaskStateDto.class,
            responseContainer = "List"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = TaskStateDto.class, responseContainer = "List"),
                    @ApiResponse(code = 401, message = "Клиент не авторизован"),
                    @ApiResponse(code = 403, message = "Нет прав"),
                    @ApiResponse(code = 404, message = "Ресурс отсутствует")
            }
    )
    @GetMapping
    List<TaskStateDto> getAllTaskStates();
}
