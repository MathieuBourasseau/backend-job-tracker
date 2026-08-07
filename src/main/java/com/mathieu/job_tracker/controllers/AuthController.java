package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.LoginCreateDto;
import com.mathieu.job_tracker.dto.LoginResponseDto;
import com.mathieu.job_tracker.services.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    // Service required for login function
    private final AuthService authService;

    // Constructor
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // POST route to log a user 
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> logUser(@Valid @RequestBody LoginCreateDto dto){
        LoginResponseDto user = authService.login(dto);
        return ResponseEntity.ok(user);
    }
}
