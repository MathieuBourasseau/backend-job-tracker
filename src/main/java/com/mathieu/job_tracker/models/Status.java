package com.mathieu.job_tracker.models;

import java.sql.Timestamp;

import jakarta.persistence.*;

// Create Status entity

@Entity
@Table(name="status")
public class Status {
    
    // Id is auto-incremented with the DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // State value is obligatory
    @Column(nullable = false, length = 50)
    private String state;

    // Date and time of the status change is obligatory
    @Column(nullable = false)
    private Timestamp date;

    // Foreign Key for application_id
    @ManyToOne
    @JoinColumn(name="application_id", nullable = false)
    private Application application;

    // Constructors
    public Status() {}

    public Status(String state, Timestamp date, Application application){
        this.state = state;
        this.date = date;
        this.application = application;
    }

    // Getters and setters
    public Long getId(){
        return this.id;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    public Timestamp getDate(){
        return this.date;
    }

    public void setDate(Timestamp date){
        this.date = date;
    }

    public Application getApplication(){
        return this.application;
    }

    public void setApplication(Application application){
        this.application = application;
    }

}
