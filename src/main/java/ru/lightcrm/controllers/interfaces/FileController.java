package ru.lightcrm.controllers.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Api(value = "/api/v1/files", tags = "Контроллер для загрузки и скачивания файлов")
@RequestMapping("/api/v1/files")
public interface FileController {

    @ApiOperation(value = "Сохранение нового файла", httpMethod = "POST", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 201, message = "Файл успешно загружен на сервер"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @PostMapping
    ResponseEntity<?> upload(@ApiParam(value = "Файл", name = "attachment", required = true) @RequestParam MultipartFile attachment);

    @ApiOperation(value = "Сохранение фото профиля", httpMethod = "POST", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 201, message = "Фото успешно загружено на сервер"),
            @ApiResponse(code = 400, message = "Некорректное тело запроса"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @PostMapping("/photo")
    ResponseEntity<?> uploadPhoto(@ApiParam(value = "Файл", name = "attachment", required = true) @RequestParam MultipartFile attachment, Principal principal);


    @ApiOperation(value = "Возвращает файл по указанному id", httpMethod = "GET", produces = MediaType.MULTIPART_FORM_DATA_VALUE, response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Файл с указанным id не найден")
    })
    @GetMapping(path = "/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> download(@ApiParam(value = "Уникальный идентификатор файла", name = "id", required = true, example = "1") @PathVariable("id") Long id);

    @ApiOperation(value = "Возвращает фото авторизованного пользователя", httpMethod = "GET", produces = MediaType.MULTIPART_FORM_DATA_VALUE, response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @GetMapping(path = "/photo", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> downloadPhoto(Principal principal);

    @ApiOperation(value = "Возвращает превью фото авторизованного пользователя", httpMethod = "GET", produces = MediaType.MULTIPART_FORM_DATA_VALUE, response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @GetMapping(path = "/photo/preview", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> downloadPreview(Principal principal);

    @ApiOperation(value = "Возвращает превью фото пользователя по id профиля", httpMethod = "GET", produces = MediaType.MULTIPART_FORM_DATA_VALUE, response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Клиент не авторизован"),
            @ApiResponse(code = 403, message = "Нет прав"),
            @ApiResponse(code = 404, message = "Ресурс отсутствует")
    })
    @GetMapping(path = "/photo/preview/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> downloadPreviewByProfileId(@ApiParam(value = "Уникальный идентификатор профиля", name = "id", required = true, example = "1") @PathVariable("id") Long id);

}
