package com.mathieu.job_tracker.dto;

import com.mathieu.job_tracker.models.StatusState;

import jakarta.validation.constraints.NotNull;

public class StatusCreateDto {

    // Fields the client is allowed to provide
    @NotNull(message = "Un état est requis.")
    private StatusState state;

    @NotNull(message = "L'id de la candidature est requis.")
    private Long applicationId;

    // Constructors
    public StatusCreateDto() {}

    public StatusCreateDto(StatusState state, Long applicationId){
        this.state = state;
        this.applicationId = applicationId;
    }

    // Getters and setters
    public StatusState getState(){
        return this.state;
    }

    public void setState(StatusState state){
        this.state = state;
    }

    public Long getApplicationId(){
        return this.applicationId;
    }

    public void setApplicationId(Long applicationId){
        this.applicationId = applicationId;
    }

}