package com.mathieu.job_tracker.exceptions;

// Class to handle login exceptions
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message){
        super(message);
    }
}
