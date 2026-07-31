package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
    
}