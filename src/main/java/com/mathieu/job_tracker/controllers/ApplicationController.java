package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.services.ApplicationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    
    // Service required
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    // POST route to create a user's application
    @PostMapping
    public ResponseEntity<ApplicationResponseDto> createApplication(Authentication authentication, @Valid @RequestBody ApplicationCreateDto dto){
        Long userId = (Long) authentication.getPrincipal();
        ApplicationResponseDto createdApplication = applicationService.createApplication(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
    }

    // GET route to get all user applications
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getUserApplications(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        List<ApplicationResponseDto> userApplications = applicationService.getUserApplications(userId);
        return ResponseEntity.ok(userApplications);
    }

    // GET route to get an application by its id
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponseDto> getApplicationById(Authentication authentication, @PathVariable Long applicationId){
        Long userId = (Long) authentication.getPrincipal();
        ApplicationResponseDto selectedApplication = applicationService.getApplicationById(userId, applicationId);
        return ResponseEntity.ok(selectedApplication);
    }

    // PUT route to update an application
    @PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponseDto> updateApplication(@PathVariable Long applicationId, @Valid @RequestBody ApplicationCreateDto dto){
        ApplicationResponseDto updatedApplication = applicationService.updateApplication(applicationId, dto);
        return ResponseEntity.ok(updatedApplication);
    }

    // DELETE route to delete an application
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId){
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}
