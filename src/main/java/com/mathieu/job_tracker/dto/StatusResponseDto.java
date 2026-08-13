package com.mathieu.job_tracker.dto;

import com.mathieu.job_tracker.models.StatusState;

import java.sql.Timestamp;

public class StatusResponseDto {

    // Data the backend chooses to provide to client
    private Long id;
    private StatusState state;
    private Timestamp date;

    // Constructors
    public StatusResponseDto() {}

    public StatusResponseDto(Long id, StatusState state, Timestamp date){
        this.id = id;
        this.state = state;
        this.date = date;
    }

    // Getters
    public Long getId(){
        return this.id;
    }

    public StatusState getState(){
        return this.state;
    }

    public Timestamp getDate(){
        return this.date;
    }
    
}
