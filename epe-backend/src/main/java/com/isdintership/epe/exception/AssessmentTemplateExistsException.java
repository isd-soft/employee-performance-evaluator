package com.isdintership.epe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssessmentTemplateExistsException extends RuntimeException {
    public AssessmentTemplateExistsException(String message) {
        super(message);
    }
}
