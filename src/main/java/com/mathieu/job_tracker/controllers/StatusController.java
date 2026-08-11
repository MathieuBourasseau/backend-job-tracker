package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.services.StatusService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    // Service required
    private final StatusService statusService;

    // Constructor
    public StatusController(StatusService statusService){
        this.statusService = statusService;
    }

    // POST route to create a new status
    @PostMapping
    public ResponseEntity<ApplicationResponseDto> createStatus(Authentication authentication, @Valid @RequestBody StatusCreateDto dto){
        Long userId = (Long) authentication.getPrincipal();
        ApplicationResponseDto createdStatus = statusService.createStatus(userId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }
}
