package com.mathieu.job_tracker.dto;

public class LoginResponseDto {

    // Data to build the LoginResponseDto
    private final String token;
    private final String email;
    private final Long id;

    // Constructor
     public LoginResponseDto(String token, String email, Long id) {
        this.token = token;
        this.email = email;
        this.id = id;
    }

    // Getters
    public String getToken() {
        return this.token;
    }

    public String getEmail() {
        return this.email;
    }

    public Long getId() {
        return this.id;
    }

}
