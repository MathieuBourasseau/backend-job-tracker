package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.models.Status;
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


    
}
