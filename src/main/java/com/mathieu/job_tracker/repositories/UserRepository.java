package com.mathieu.job_tracker.repositories;

import com.mathieu.job_tracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Empty interface: Spring Data JPA generates the implementation (save, findAll, findById, deleteById...)
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByEmail(String email);
}