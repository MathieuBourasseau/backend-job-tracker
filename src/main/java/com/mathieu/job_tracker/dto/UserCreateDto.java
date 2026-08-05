package com.mathieu.job_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class UserCreateDto {

    // Fields the client is allowed to provide

    @NotBlank(message = "Une adresse mail est requise.")
    private String email;

    @NotBlank(message = "Un mot de passe est requis.")
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
