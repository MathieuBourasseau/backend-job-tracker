package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.services.ApplicationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            ApplicationResponseDto createdApplication = applicationService.createApplication(dto, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    // Route to get all user's application
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ApplicationResponseDto>> getUserApplications(@PathVariable Long userId){
        List<ApplicationResponseDto> userApplications = applicationService.getUserApplications(userId);
        return ResponseEntity.ok(userApplications);
    }
}
