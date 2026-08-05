package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.services.ApplicationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    
    // Service required
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    // Route to create user's application
    @PostMapping("/users/{userId}")
    public ResponseEntity<ApplicationResponseDto> createApplication(@PathVariable Long userId, @RequestBody ApplicationCreateDto dto){
        ApplicationResponseDto createdApplication = applicationService.createApplication(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
    }
}
