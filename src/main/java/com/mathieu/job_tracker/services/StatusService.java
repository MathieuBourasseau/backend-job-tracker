package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.models.Status;
import com.mathieu.job_tracker.models.Application;
import com.mathieu.job_tracker.repositories.StatusRepository;
import com.mathieu.job_tracker.repositories.ApplicationRepository;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    // Repositories required
    private final StatusRepository statusRepository;
    private final ApplicationRepository applicationRepository;

    public StatusService(StatusRepository statusRepository, ApplicationRepository applicationRepository){
        this.statusRepository = statusRepository;
        this.applicationRepository = applicationRepository;
    }

    // Method to add a new status

    public ApplicationResponseDto createStatus(StatusCreateDto dto)

    // Get the id from dto and check if there is an existing application
    // If not throw error
    Application application = applicationRepository.findById(dto.getApplicationId())
        .orElseThrow(() -> new RuntimeException("Cette candidature n'existe pas."));


    // Create a new status entity

    // Save this entity in DB

    // Create ApplicationResponseDto

    
}
