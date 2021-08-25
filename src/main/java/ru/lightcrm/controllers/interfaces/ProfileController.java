package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lightcrm.entities.dtos.ProfileDto;
import ru.lightcrm.entities.dtos.ProfileFullDto;
import ru.lightcrm.entities.dtos.ProfileMiniDto;
import ru.lightcrm.entities.dtos.SystemUserDto;

import java.security.Principal;
import java.util.List;

@Api(value = "/api/v1/profiles", tags = "Контроллер для работы с профилями", produces = "application/json")
@RequestMapping(value = "/api/v1/profiles", produces = "application/json")
public interface ProfileController {

    @ApiOperation(value = "Возвращает профиль c основными характеристиками по указанному id", httpMethod = "GET", produces = "application/json", response = ProfileDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileDto.class),
            @ApiResponse(code = 400, message = "Указан некорректны id"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Профиль с указанным id отсутствует")
    })
    @GetMapping(value = "/{id}")
    ProfileDto getById(@ApiParam(value = "Уникальный идентификатор профиля", name = "id", required = true, example = "1") @PathVariable Long id);

    @ApiOperation(value = "Возвращает список всех профилей с основными характеристиками", httpMethod = "GET", produces = "application/json", response = ProfileDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileDto.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @GetMapping
    List<ProfileDto> getAll();

    @ApiOperation(value = "Возвращает профиль с подробными характеристиками по указанному id", httpMethod = "GET", produces = "application/json", response = ProfileFullDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileFullDto.class),
            @ApiResponse(code = 400, message = "Указан некорректны id"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Профиль с указанным id отсутствует")
    })
    @GetMapping(value = "/full/{id}")
    ProfileFullDto getProfileFullById(@ApiParam(value = "Уникальный идентификатор профиля", name = "id", required = true, example = "1") @PathVariable Long id);

    @ApiOperation(value = "Возвращает список всех профилей с подробными характеристиками", httpMethod = "GET", produces = "application/json", response = ProfileFullDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileFullDto.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @GetMapping(value = "/full")
    List<ProfileFullDto> getAllProfilesFull();

    @ApiOperation(value = "Возвращает профиль с подробными характеристиками авторизованного пользователя", httpMethod = "GET", produces = "application/json", response = ProfileFullDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileFullDto.class),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Пользователь userId не найден")
    })
    @GetMapping("/profile")
    ProfileFullDto getProfileFull(Principal principal);

    @ApiOperation(value = "Возвращает профиль с базовыми характеристиками по указанному id", httpMethod = "GET", produces = "application/json", response = ProfileMiniDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileMiniDto.class),
            @ApiResponse(code = 400, message = "Указан некорректны id"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Профиль с указанным id отсутствует")
    })
    @GetMapping(value = "/mini/{id}")
    ProfileMiniDto getProfileMiniById(@ApiParam(value = "Уникальный идентификатор профиля", name = "id", required = true, example = "1") @PathVariable Long id);

    @ApiOperation(value = "Возвращает список всех профилей с базовыми характеристиками", httpMethod = "GET", produces = "application/json", response = ProfileMiniDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileMiniDto.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @GetMapping(value = "/mini")
    List<ProfileMiniDto> getAllProfilesMini();

    @ApiOperation(value = "Возвращает профиль с базовыми характеристиками авторизованного пользователя", httpMethod = "GET", produces = "application/json", response = ProfileMiniDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProfileMiniDto.class),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Пользователь userId не найден")
    })
    @GetMapping("/profile/mini")
    ProfileMiniDto getProfileMini(Principal principal);

    @ApiOperation(value = "Регистрация нового пользователя", httpMethod = "POST", consumes = "application/json", produces = "application/json", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class),
            @ApiResponse(code = 201, message = "Новый пользователь успешно создан", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует"),
            @ApiResponse(code = 415, message = "Неподдерживаемый тип данных")
    })
    @PostMapping(value = "/register", consumes = "application/json")
    ResponseEntity<?> saveNewUser(
            @ApiParam(value = "JSON представление данных нового пользователя", name = "systemUserDto", required = true,
                    example = "{\"firstname\": \"Иван\", \"lastname\": \"Иванов\", \"middlename\": \"Иванович\", " +
                            "\"staffUnitName\": \"string\", \"employmentDate\": \"2012-10-23\", \"departmentNames\": [\"string\"], " +
                            "\"login\": \"Aladdin\", \"password\": \"12345\", \"confirmationPassword\": \"12345\"}")
            @RequestBody @Validated SystemUserDto systemUserDto, BindingResult bindingResult
    );
}

