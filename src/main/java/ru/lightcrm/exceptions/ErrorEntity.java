package ru.lightcrm.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorEntity {
    private int status;
    private String message;
    private Date timestamp;

    public ErrorEntity(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
