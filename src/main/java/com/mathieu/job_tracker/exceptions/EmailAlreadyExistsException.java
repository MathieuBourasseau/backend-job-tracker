package com.mathieu.job_tracker.exceptions;

// Class to handle email already existing
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
