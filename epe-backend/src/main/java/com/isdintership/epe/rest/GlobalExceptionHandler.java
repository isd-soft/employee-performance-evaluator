package com.isdintership.epe.rest;

import com.isdintership.epe.dto.ErrorResponse;
import com.isdintership.epe.entity.exception.JobNotFoundException;
import com.isdintership.epe.entity.exception.RoleNotFoundException;
import com.isdintership.epe.entity.exception.UserExistsException;
import com.isdintership.epe.entity.exception.UserNotFoundException;
import com.isdintership.epe.security.jwt.JwtAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExistsException(UserExistsException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleJobNotFoundException(JobNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
