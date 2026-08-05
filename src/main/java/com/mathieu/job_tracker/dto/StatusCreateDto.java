package com.mathieu.job_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StatusCreateDto {

    // Fields the client is allowed to provide
    @NotBlank(message = "Un état est requis.")
    private String state;

    @NotNull(message = "L'id de la candidature est requis.")
    private Long applicationId;

    // Constructors
    public StatusCreateDto() {}

    public StatusCreateDto(String state, Long applicationId){
        this.state = state;
        this.applicationId = applicationId;
    }

    // Getters and setters
    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    public Long getApplicationId(){
        return this.applicationId;
    }

    public void setApplicationId(Long applicationId){
        this.applicationId = applicationId;
    }

}