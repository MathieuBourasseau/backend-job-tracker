package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.services.StatusService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class StatusController {

    // Service required
    private final StatusService statusService;

    // Constructor
    public StatusController(StatusService statusService){
        this.statusService = statusService;
    }
    
}
