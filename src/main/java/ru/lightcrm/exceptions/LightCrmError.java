package ru.lightcrm.exceptions;

import java.util.Date;
import lombok.Data;

@Data
public class LightCrmError {
    private  int status;
    private String msg;
    private Date date;

    public LightCrmError(int status, String msg) {
        this.status = status;
        this.msg = msg;
        this.date = new Date();
    }
}
