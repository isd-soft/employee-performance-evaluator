package com.isdintership.epe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssessmentStatusNotFound extends RuntimeException {
    public AssessmentStatusNotFound(String message) {
        super(message);
    }
}
