package com.mathieu.job_tracker.models;

import jakarta.persistence.*;

// Create the entity Company
@Entity
@Table(name = "companies")
public class Company{
    
    // Id is auto-incremented with the DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name is obligatory
    @Column(nullable = false)
    private String name;

    // Activity is obligatory
    @Column(nullable = false)
    private String activity;

    public Company() {}

    // Constructor
    public Company(String name, String activity){
        this.name = name;
        this.activity = activity;
    }

    // Getters and setters
    public Long getId(){
        return this.id;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getActivity(){
        return this.activity;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }
    
}
