package com.mathieu.job_tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Controller to handle all kind of exception from every controllers
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Used to properly JSON-encode the plain error messages below (quotes, escaping)
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Turns a plain message into a valid JSON string body, e.g. Refus -> "Refus"
    private String toJsonString(String message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }

    // Exception and JSON response in case of not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException e) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(toJsonString(e.getMessage()));
    }

    // Exception and JSON response in case of email already existing exception
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException e) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(toJsonString(e.getMessage()));
    }

    // Exception and JSON response in case of failure while trying to login
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException e) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(toJsonString(e.getMessage()));
    }

}
