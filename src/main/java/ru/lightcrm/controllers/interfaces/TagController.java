package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import ru.lightcrm.entities.dtos.TagDto;

import java.util.List;

@Api(value = "/api/v1/tags", tags = "Контроллер для работы с тегами", produces = "application/json")
public interface TagController {
    @GetMapping("/api/v1/tags")
    @ApiOperation(value = " Возвращает cписок DTO тегов",
            notes = "Запрос списка тегов",
            httpMethod = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagDto.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс не найден")
    })
    List<TagDto> getTagContent();

}
