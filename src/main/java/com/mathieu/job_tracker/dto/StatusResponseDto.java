package com.mathieu.job_tracker.dto;

import java.sql.Timestamp;

public class StatusResponseDto {
    
    // Data the backend chooses to provide to client
    private Long id;
    private String state;
    private Timestamp date;

    // Constructors
    public StatusResponseDto() {}

    public StatusResponseDto(Long id, String state, Timestamp date){
        this.id = id;
        this.state = state;
        this.date = date;
    }

    // Getters
    public Long getId(){
        return this.id;
    }

    public String getState(){
        return this.state;
    }

    public Timestamp getDate(){
        return this.date;
    }
    
}
