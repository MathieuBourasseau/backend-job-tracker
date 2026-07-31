package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
    
}