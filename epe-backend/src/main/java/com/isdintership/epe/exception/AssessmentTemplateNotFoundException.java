package com.isdintership.epe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssessmentTemplateNotFoundException extends  RuntimeException {
    public AssessmentTemplateNotFoundException(String message) {
        super(message);
    }
}
