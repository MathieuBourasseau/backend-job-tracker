package com.mathieu.job_tracker.dto;

public class UserResponseDto {

    // Fields the backend chooses to provide to client
    private String email;
    private Long id;

    // Constructors
    public UserResponseDto() {}

    public UserResponseDto(String email, Long id){
        this.email = email;
        this.id= id;
    }

    // Getters
    public String getEmail(){
        return this.email;
    }

    public Long getId(){
        return this.id;
    }

}
