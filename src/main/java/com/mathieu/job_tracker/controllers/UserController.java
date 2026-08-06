package com.mathieu.job_tracker.controllers;

import com.mathieu.job_tracker.dto.UserCreateDto;
import com.mathieu.job_tracker.dto.UserResponseDto;
import com.mathieu.job_tracker.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // Services required for the use of the controller
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // POST route to create a user
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto){
        UserResponseDto createdUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
