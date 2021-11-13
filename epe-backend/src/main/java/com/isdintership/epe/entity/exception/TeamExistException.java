package com.isdintership.epe.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TeamExistException extends RuntimeException {
    public TeamExistException(String message) {
        super(message);
    }
}
