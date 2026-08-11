package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.exceptions.ResourceNotFoundException;
import com.mathieu.job_tracker.models.Application;
import com.mathieu.job_tracker.models.Company;
import com.mathieu.job_tracker.models.Status;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.ApplicationRepository;
import com.mathieu.job_tracker.repositories.CompanyRepository;
import com.mathieu.job_tracker.repositories.StatusRepository;
import com.mathieu.job_tracker.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    // Repository required to get access to methods needed
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    // ApplicationService needs ApplicationRepository to work
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository,
            CompanyRepository companyRepository, StatusRepository statusRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    // ApplicationService methods

    // --- CREATE APPLICATION METHOD ---
    public ApplicationResponseDto createApplication(ApplicationCreateDto dto, Long userId) {

        // Find user in DB with the id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        // Check if the company is already existing || Create the company if not found
        Company company = companyRepository.findByName(dto.getCompanyName())
                .orElseGet(() -> companyRepository.save(new Company(dto.getCompanyName(), dto.getCompanyActivity())));

        // Create a new application entity
        Application newApplication = new Application(dto.getLink(), dto.getContact(), dto.getJobTitle(),
                dto.getLocation(), dto.getSalary(), dto.getContract(), dto.getApplicationDate(), null, null, false,
                null, user, company);

        // Save the entity created
        Application savedApplication = applicationRepository.save(newApplication);

        // Create the initial status entry ("A faire") for this application
        Status initialStatus = new Status("A faire", new Timestamp(System.currentTimeMillis()), savedApplication);
        Status savedStatus = statusRepository.save(initialStatus);

        List<StatusResponseDto> statuses = List.of(
                new StatusResponseDto(savedStatus.getId(), savedStatus.getState(), savedStatus.getDate()));

        // Response Dto
        ApplicationResponseDto response = new ApplicationResponseDto(
                savedApplication.getId(),
                savedApplication.getLink(),
                savedApplication.getContact(),
                savedApplication.getJobTitle(),
                savedApplication.getLocation(),
                savedApplication.getSalary(),
                savedApplication.getContract(),
                savedApplication.getApplicationDate(),
                savedApplication.getApplicationReSubmissionDate(),
                savedApplication.getApplicationReSubmissionDate2(),
                savedApplication.getInterview(),
                savedApplication.getRefusalReason(),
                company.getName(),
                company.getActivity(),
                company.getId(),
                statuses);

        return response;
    }

    // --- GET USER'S APPLICATIONS METHOD ---

    public List<ApplicationResponseDto> getUserApplications(Long userId) {

        // Get all user's application with the userId
        List<Application> applications = applicationRepository.findByUserId(userId);

        // Create an array list with full applications (data and statuses)
        List<ApplicationResponseDto> result = new ArrayList<>();

        for (Application application : applications) {

            // Get statuses historic for each application
            List<Status> statusList = statusRepository.findByApplicationId(application.getId());

            // List that will hold the statuses converted for the response
            List<StatusResponseDto> statusResponseDtos = new ArrayList<>();

            // Convert each status entity into a StatusResponseDto
            for (Status status : statusList) {
                statusResponseDtos.add(new StatusResponseDto(status.getId(), status.getState(), status.getDate()));
            }

            // Build the response DTO for this application
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
                    statusResponseDtos);

            // Add this application's DTO to the final result list
            result.add(responseDto);

        }

        return result;
    }

    // --- GET APPLICATION BY ID METHOD --- 

    public ApplicationResponseDto getApplicationById(Long userId, Long id){

        // Checking the id
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidature non trouvée"));

        // Checking that application user id matches with the current user logged
        if(!application.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Candidature non trouvée");
        }

        // Get statuses of this application and convert it in StatusResponseDto
        List<Status> listStatus = statusRepository.findByApplicationId(id);

        List<StatusResponseDto> statusResponseDtos = new ArrayList<>();
        for(Status status : listStatus){
            statusResponseDtos.add(new StatusResponseDto(
                status.getId(),
                status.getState(),
                status.getDate()
            ));
               
        }
       
        // Create responseDto
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
                statusResponseDtos
        );

        return responseDto;
    }

    //  --- UPDATE APPLICATION METHOD --- 

    public ApplicationResponseDto updateApplication(Long id, ApplicationCreateDto dto){

        // Find the existing application, or fail if it doesn't exist
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidature non trouvée"));

        // Find or create the company from the updated dto
        Company company = companyRepository.findByName(dto.getCompanyName())
                .orElseGet(() -> companyRepository.save(new Company(dto.getCompanyName(), dto.getCompanyActivity())));

        // Apply the new values to the existing entity
        existingApplication.setLink(dto.getLink());
        existingApplication.setContact(dto.getContact());
        existingApplication.setJobTitle(dto.getJobTitle());
        existingApplication.setLocation(dto.getLocation());
        existingApplication.setSalary(dto.getSalary());
        existingApplication.setContract(dto.getContract());
        existingApplication.setApplicationDate(dto.getApplicationDate());
        existingApplication.setCompany(company);

        Application savedApplication = applicationRepository.save(existingApplication);

        // Get statuses of this application and convert them to StatusResponseDto
        List<Status> listStatus = statusRepository.findByApplicationId(savedApplication.getId());
        List<StatusResponseDto> statusResponseDtos = new ArrayList<>();
        for (Status status : listStatus) {
            statusResponseDtos.add(new StatusResponseDto(status.getId(), status.getState(), status.getDate()));
        }

        // Create responseDto
        ApplicationResponseDto responseDto = new ApplicationResponseDto(
                savedApplication.getId(),
                savedApplication.getLink(),
                savedApplication.getContact(),
                savedApplication.getJobTitle(),
                savedApplication.getLocation(),
                savedApplication.getSalary(),
                savedApplication.getContract(),
                savedApplication.getApplicationDate(),
                savedApplication.getApplicationReSubmissionDate(),
                savedApplication.getApplicationReSubmissionDate2(),
                savedApplication.getInterview(),
                savedApplication.getRefusalReason(),
                company.getName(),
                company.getActivity(),
                company.getId(),
                statusResponseDtos);

        return responseDto;
    }

    // --- DELETE APPLICATION METHOD ---
    
    public void deleteApplication(Long id){

        // Find existing candidature
        Application existingApplication = applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Candidature non trouvée."));

        // Find all status
        List<Status> listStatus = statusRepository.findByApplicationId(id);
        statusRepository.deleteAll(listStatus);
        
        // Delete all application data
        applicationRepository.delete(existingApplication);
        
    }

}
