package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.Company;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Empty interface: Spring Data JPA generates the implementation (save, findAll, findById, deleteById...)
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

    // Method to search a company by its name
    Optional<Company> findByName(String name); 

}