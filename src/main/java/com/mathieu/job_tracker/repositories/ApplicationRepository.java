package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{
    
}