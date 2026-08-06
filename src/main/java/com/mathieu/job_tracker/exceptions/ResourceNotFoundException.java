package com.mathieu.job_tracker.exceptions;

// Class to handle not found exception type
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}
