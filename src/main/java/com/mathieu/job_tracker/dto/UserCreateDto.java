package com.mathieu.job_tracker.dto;

public class UserCreateDto {

    // Fields the client is allowed to provide
    private String email;
    private String password;

    // Constructors
    public UserCreateDto() {}

    public UserCreateDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
}
