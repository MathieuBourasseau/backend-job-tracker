package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.Application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Empty interface: Spring Data JPA generates the implementation (save, findAll, findById, deleteById...)
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{

    // Method added in addition to the CRUD methods of JPA
    List<Application> findByUserId(Long userId);
    
}