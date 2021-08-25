package ru.lightcrm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    private static final Pattern fileSizeFromErrMsgPattern = Pattern.compile("(\\d+)");
    private static final String fileSizeLimitMessage = "Размер загружаемого файла (%d KB) превышает допустимый лимит (%d KB)";

    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        ErrorEntity err = new ErrorEntity(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        log.error(e.getMessage());
        LightCrmError err = new LightCrmError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String message = "Неподдерживаемый тип данных";
        log.error(message, e);
        LightCrmError err = new LightCrmError(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSizeLimitExceededException(SizeLimitExceededException e) {
        log.error(e.getMessage());
        LightCrmError err = new LightCrmError(HttpStatus.BAD_REQUEST.value(), getLimitFileSizeMessage(e.getMessage()));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    private String getLimitFileSizeMessage(String errorMessage) {
        ArrayList<String> list = new ArrayList<>();
        Matcher matcher = fileSizeFromErrMsgPattern.matcher(errorMessage);
        while (matcher.find()) {
            list.add(matcher.group(1));
        }
        int fileSize = Integer.parseInt(list.get(0)) / (1024);
        int limitSize = Integer.parseInt(list.get(1)) / (1024);
        return String.format(fileSizeLimitMessage, fileSize, limitSize);
    }
}
