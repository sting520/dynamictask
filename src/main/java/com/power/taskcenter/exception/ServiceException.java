package com.power.taskcenter.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -1318947966974276849L;

    @Getter
    @Setter
    private int errCode = HttpStatus.BAD_REQUEST.value();

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, int errCode) {
        this(msg);
        this.errCode = errCode;
    }
}
