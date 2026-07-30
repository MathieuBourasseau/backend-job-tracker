package com.mathieu.job_tracker.models;

import jakarta.persistence.*;

// Create the entity users
@Entity
@Table(name = "users")
public class User {
    
    // Id is auto-incremented with the DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Email is obligatory
    @Column(nullable = false, unique = true)
    private String email;

    // Password is obligatory
    @Column(nullable = false)
    private String password;

    public User() {}

    // Constructor
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Long getId(){
        return this.id;
    }


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
