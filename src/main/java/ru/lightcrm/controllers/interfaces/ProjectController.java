package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import ru.lightcrm.entities.dtos.ProjectDto;

import java.util.List;
import java.util.Map;

@Api(value = "/api/v1/projects", tags = "Контроллер для работы с проектами", produces = "application/json")
@RequestMapping(value = "/api/v1/projects", produces = "application/json")
public interface ProjectController {

    @ApiOperation(value = "Получить список проектов",
            httpMethod = "GET",
            produces = "application/json",
            response = ProjectDto.class,
            responseContainer = "List"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = ProjectDto.class, responseContainer = "List"),
                    @ApiResponse(code = 401, message = "Клиент не авторизован"),
                    @ApiResponse(code = 403, message = "Нет прав"),
                    @ApiResponse(code = 404, message = "Ресурс отсутствует")
            }
    )
    @GetMapping
    List<ProjectDto> getAllProjects(@ApiParam(value = "Map с набором параметров и значений",  required = false, example = "responsibleId: 1") @RequestParam Map<String, String> params);

    @ApiOperation(value = "Получить проект по его Id",
            httpMethod = "GET",
            produces = "application/json",
            response = ProjectDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Указан некорректный id"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Проект с указанным id отсутствует")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ProjectDto getProjectById(@ApiParam(value = "идентификатор проекта", required = true, example = "1") @PathVariable Long id);

    @ApiOperation(value = "Сохранить новый проект",
            httpMethod = "POST",
            consumes = "application/json",
            produces = "application/json",
            response = ProjectDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProjectDto.class),
            @ApiResponse(code = 201, message = "Новый проект успешно создан", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ProjectDto saveProject(@ApiParam(value = "JSON представление данных нового проекта", required = true) @RequestBody ProjectDto projectDTO);

    @ApiOperation(value = "Обновить проект",
            httpMethod = "PUT",
            consumes = "application/json",
            produces = "application/json",
            response = ProjectDto.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProjectDto.class),
            @ApiResponse(code = 201, message = "Проект успешно изменен", response = ProjectDto.class),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @PutMapping(consumes = "application/json", produces = "application/json")
    ProjectDto updateProject(@ApiParam(value = "JSON представление данных измененного проекта", required = true) @RequestBody ProjectDto projectDTO);

    @ApiOperation(value = "Удалить проект",
            httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @DeleteMapping(value = "/{id}")
    void deleteProjectById(@ApiParam(value = "идентификатор удаляемого проекта", required = true, example = "1") @PathVariable Long id);

}
