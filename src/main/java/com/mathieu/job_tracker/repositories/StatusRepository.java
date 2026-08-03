package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.Status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Empty interface: Spring Data JPA generates the implementation (save, findAll, findById, deleteById...)
@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
    // Method to get an application by its id
    List<Status>findByApplicationId(Long id);
}