package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.exceptions.ResourceNotFoundException;
import com.mathieu.job_tracker.models.Status;
import com.mathieu.job_tracker.models.Application;
import com.mathieu.job_tracker.repositories.StatusRepository;
import com.mathieu.job_tracker.repositories.ApplicationRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    // Repositories required
    private final StatusRepository statusRepository;
    private final ApplicationRepository applicationRepository;

    public StatusService(StatusRepository statusRepository, ApplicationRepository applicationRepository) {
        this.statusRepository = statusRepository;
        this.applicationRepository = applicationRepository;
    }

    // --- METHOD TO ADD CREATE A NEW STATUS ---

    public ApplicationResponseDto createStatus(Long userId, StatusCreateDto dto){

        // Get the id from dto and check if there is an existing application
        // If not throw error
        Application application = applicationRepository.findById(dto.getApplicationId())
        .orElseThrow(() -> new ResourceNotFoundException("Cette candidature n'existe pas."));

        // Checking before to create a new status that the current user corresponding to the application found
        if(!application.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Candidature non trouvée");
        }


        // Create a new status entity
        Status newStatus = new Status(dto.getState(), new Timestamp(System.currentTimeMillis()), application);

        // Save this entity in DB
        statusRepository.save(newStatus);

        // Find all previous status linked to the application
        List<Status> listStatus = statusRepository.findByApplicationId(dto.getApplicationId());

        // Create a array to send all statuses
        List<StatusResponseDto> statusReponseDtos = new ArrayList<>();
        for(Status status : listStatus){
            statusReponseDtos.add(new StatusResponseDto(
                status.getId(),
                status.getState(),
                status.getDate()
            ));
        }

        // Create ApplicationResponseDto
        ApplicationResponseDto responseDto = new ApplicationResponseDto(
                application.getId(),
                application.getLink(),
                application.getContact(),
                application.getJobTitle(),
                application.getLocation(),
                application.getSalary(),
                application.getContract(),
                application.getApplicationDate(),
                application.getApplicationReSubmissionDate(),
                application.getApplicationReSubmissionDate2(),
                application.getInterview(),
                application.getRefusalReason(),
                application.getCompany().getName(),
                application.getCompany().getActivity(),
                application.getCompany().getId(),
                statusReponseDtos
        );

        return responseDto;

    }

}
