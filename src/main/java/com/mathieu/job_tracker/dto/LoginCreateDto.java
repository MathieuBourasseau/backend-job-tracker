package com.mathieu.job_tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginCreateDto {
    
    // Data connection sent from clients

    @NotBlank(message = "Une adresse mail est requise.")
    private String email;

    @NotBlank(message = "Un mot de passe est requis.")
    private String password;

    // Constructors

    public LoginCreateDto() {}

    public LoginCreateDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getters et Setters
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
