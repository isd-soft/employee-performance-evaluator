package com.isdintership.epe.controller;

import com.isdintership.epe.dto.ErrorResponse;
import com.isdintership.epe.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

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


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(TeamExistException.class)
    public ResponseEntity<ErrorResponse> handleTeamExistException(TeamExistException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AssessmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAssessmentNotFoundException(AssessmentNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(SubordinatesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubordinatesNotFoundException(SubordinatesNotFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().title(e.getMessage()).details(request.getRequestURI()).build();
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
