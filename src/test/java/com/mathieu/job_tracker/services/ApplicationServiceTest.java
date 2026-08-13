package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.exceptions.ResourceNotFoundException;
import com.mathieu.job_tracker.models.Application;
import com.mathieu.job_tracker.models.Company;
import com.mathieu.job_tracker.models.Status;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.ApplicationRepository;
import com.mathieu.job_tracker.repositories.CompanyRepository;
import com.mathieu.job_tracker.repositories.StatusRepository;
import com.mathieu.job_tracker.repositories.UserRepository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    // Mocks needed to create ApplicationServiceTest

        // User repository
        @Mock
        private UserRepository userRepository;

        // Company repository
        @Mock
        private CompanyRepository companyRepository;

        // Status repository
        @Mock
        private StatusRepository statusRepository;

        // Application repository
        @Mock
        private ApplicationRepository applicationRepository;

        // Create ApplicationServiceTest
        @InjectMocks
        private ApplicationService applicationService;

    
    // --- TEST : CREATEAPPLICATION METHOD SUCCEEDS ---
    @Test
    void createApplication_shouldSucceed_WhenUserAndCompanyAreValid(){

        // Arrange data required

            // ApplicationCreateDto
            ApplicationCreateDto dto = new ApplicationCreateDto(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                "Acme", "Tech"
            );

            // Existing user
            User existingUser = new User("test@test.com", "hashedPassword");
            ReflectionTestUtils.setField(existingUser, "id", 1L);

            // Existing company
            Company existingCompany = new Company("Acme","Prestation de service informatique");
            ReflectionTestUtils.setField(existingCompany, "id", 1L);

            // First check succeeding by returning user by id
            when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

            // Second check succeeding by returning company by its name
            when(companyRepository.findByName("Acme")).thenReturn(Optional.of(existingCompany));

            // Simulate the application that should be returned by save
            Application savedApplication = new Application(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                null, null, false, null,
                existingUser, existingCompany
            );
            ReflectionTestUtils.setField(savedApplication, "id", 1L);

            when(applicationRepository.save(org.mockito.ArgumentMatchers.any(Application.class))).thenReturn(savedApplication);

            // Simulate the status that should be returned by save
            Status savedStatus = new Status("A faire", new java.sql.Timestamp(System.currentTimeMillis()), savedApplication);
            ReflectionTestUtils.setField(savedStatus, "id", 1L);

            when(statusRepository.save(org.mockito.ArgumentMatchers.any(Status.class))).thenReturn(savedStatus);
        
        // Act : by using createApplication method
            
            ApplicationResponseDto result = applicationService.createApplication(dto, 1L);
        
        // Assert : return essential data from dto
            
            // Application id
            assertEquals(1L, result.getId());

            // Job title
            assertEquals("jobTitle", result.getJobTitle());

            // Company Name
            assertEquals("Acme", result.getCompanyName());

            // List of statuses
            assertEquals(1, result.getStatuses().size());
            assertEquals("A faire", result.getStatuses().get(0).getState());
    }

    // --- TEST : CREATEAPPLICATION SHOULD FAIL WHEN USER ID IS NOT FOUND --- 

    @Test
    void createApplication_shouldFail_WhenUserIdIsNotFound(){

        // Arrange date required : ApplicationCreateDto 

            // DTO
            ApplicationCreateDto dto = new ApplicationCreateDto(
                    "link", "contact", "jobTitle", "location", 1000, "CDI",
                    new java.sql.Date(System.currentTimeMillis()),
                    "Acme", "Tech"
                );
        
            // Find user by id
            when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act and assert

            // When createApplication is called with wrong userId, it should throw ResourceNotFoundException
            assertThrows(ResourceNotFoundException.class, () -> applicationService.createApplication(dto, 1L));
    }

    // --- TEST : GETUSERAPPLICATION SHOULD SUCCEED WITH A VALID USERID --- 

    @Test
    void getUserApplication_shouldSucceed_whenUserIdIsValid(){

        // Arrange data required

            // Existing list of applications
            User user = new User("test@test.com", "hashedPassword");
            ReflectionTestUtils.setField(user, "id", 1L);

            Company company = new Company("Acme", "Tech");
            ReflectionTestUtils.setField(company, "id", 1L);

            Application application = new Application(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                null, null, false, null,
                user, company
            );
            ReflectionTestUtils.setField(application, "id", 1L);

            List<Application> applications = List.of(application);

            // Existing list of statuses
            Status status = new Status("A faire", new java.sql.Timestamp(System.currentTimeMillis()), application);
            ReflectionTestUtils.setField(status, "id", 1L);

            List<Status> statuses = List.of(status);

            // Find list of applications with userId
            when(applicationRepository.findByUserId(1L)).thenReturn(applications);

            // Find list of statuses with applicationId
            when(statusRepository.findByApplicationId(1L)).thenReturn(statuses);

        // Act : when getUserApplication(userId) is called

            List<ApplicationResponseDto> result = applicationService.getUserApplications(1L);

        // Assert

            // Number of applications returned
            assertEquals(1, result.size());

            // Application id
            assertEquals(1L, result.get(0).getId());

            // Job title
            assertEquals("jobTitle", result.get(0).getJobTitle());

            // Company name
            assertEquals("Acme", result.get(0).getCompanyName());

            // Statuses
            assertEquals(1, result.get(0).getStatuses().size());
            assertEquals("A faire", result.get(0).getStatuses().get(0).getState());
    }

    // --- TEST : GETAPPLICATIONBYID SHOULD SUCCEED WITH A VALID APPLICATION ID --- 
    @Test
    void getApplicationById_shouldSucceed_WhenApplicationIdIsValid(){

        // Arrange data required : 

            // Existing user -> needed because we have to create an existing application and the user id is compared to userId in parameter
            User existingUser = new User("test@test.com", "hashedPassword");
            ReflectionTestUtils.setField(existingUser, "id", 1L);

            // Existing company -> required by the Application constructor
            Company existingCompany = new Company("Acme", "Tech");
            ReflectionTestUtils.setField(existingCompany, "id", 1L);

            // Existing application -> needed because it used to mocked applicationRepository
            Application existingApplication = new Application(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                null, null, false, null,
                existingUser, existingCompany
            );
            ReflectionTestUtils.setField(existingApplication, "id", 1L);

            // Existing status + List of statuses -> needed to mock statusRepository
            Status status = new Status("A faire", new java.sql.Timestamp(System.currentTimeMillis()), existingApplication);
            ReflectionTestUtils.setField(status, "id", 1L);

            List<Status> statuses = List.of(status);

            when(applicationRepository.findById(1L)).thenReturn(Optional.of(existingApplication));

            when(statusRepository.findByApplicationId(1L)).thenReturn(statuses);

        // Act : when getApplicationById is called
            
            ApplicationResponseDto result = applicationService.getApplicationById(1L, 1L);

        // Assert : test should succeed and return the selected application
            
            // Application id
            assertEquals(1L, result.getId());

            // Job title
            assertEquals("jobTitle", result.getJobTitle());

            // Company Name
            assertEquals("Acme", result.getCompanyName());

            // Activity
            assertEquals("Tech", result.getCompanyActivity());

            // Statuses
            assertEquals(1, result.getStatuses().size());
            assertEquals("A faire", result.getStatuses().get(0).getState());
    }

    // --- TEST : GETAPPLICATIONBYID SHOULD FAIL WHEN APPLICATION ID IS NOT FOUND --- 
    @Test
    void getApplicationById_shouldFail_WhenApplicationIdIsNotFound(){

        // Arrange data 

            when(applicationRepository.findById(2L)).thenReturn(Optional.empty());
        
        // Act and assert : when getApplicationById is called with a wrong application id, it should throw ResourceNotFoundException
            
            assertThrows(ResourceNotFoundException.class, () -> applicationService.getApplicationById(1L,2L));
    }

    // --- TEST : GETAPPLICATIONBYID SHOULD FAIL WHEN USER ID IS NOT FOUND ---
    @Test
    void getApplicationById_shouldFail_WhenUserIdIsNotFound(){

        // Arrange data 

            // Existing user -> needed to create application
            User existingUser = new User("test@test.com", "hashedPassword");
            ReflectionTestUtils.setField(existingUser, "id", 1L);

            // Existing company -> needed to create application
            Company existingCompany = new Company("Acme", "Tech");
            ReflectionTestUtils.setField(existingCompany, "id", 1L);

            // Existing application -> because in this case the application is found
            Application existingApplication = new Application(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                null, null, false, null,
                existingUser, existingCompany
            );
            ReflectionTestUtils.setField(existingApplication, "id", 1L);

            when(applicationRepository.findById(1L)).thenReturn(Optional.of(existingApplication));
        
        // Act and assert : when getApplicationById is called with wrong userId, it should throw ResourceNotFoundException
            
            assertThrows(ResourceNotFoundException.class, ()-> applicationService.getApplicationById(2L, 1L));
    }

    // --- TEST : UPDATEAPPLICATION SHOULD SUCCEED WHEN APPLICATION AND USER ARE VALID ---
    @Test
    void updateApplication_shouldSucceed_WhenApplicationAndUserAreValid(){

        // Arrange data required

            // dto
            ApplicationCreateDto dto = new ApplicationCreateDto(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                "Acme", "Tech"
            );

            // Existing user -> required to create application
             User existingUser = new User("test@test.com", "hashedPassword");
            ReflectionTestUtils.setField(existingUser, "id", 1L);

            // Existing company -> required to create application + mock companyRepository
            Company existingCompany = new Company("Acme", "Tech");
            ReflectionTestUtils.setField(existingCompany, "id", 1L);

            // Existing application -> required because the application id is valid
             Application existingApplication = new Application(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                null, null, false, null,
                existingUser, existingCompany
            );
            ReflectionTestUtils.setField(existingApplication, "id", 1L);

            // Saved application -> required to mock applicationRepository
             Application savedApplication = new Application(
                "link", "contact", "jobTitle", "location", 1000, "CDI",
                new java.sql.Date(System.currentTimeMillis()),
                null, null, false, null,
                existingUser, existingCompany
            );
            ReflectionTestUtils.setField(savedApplication, "id", 1L);

            // Existing status + List of statuses
            Status savedStatus = new Status("A faire", new java.sql.Timestamp(System.currentTimeMillis()), savedApplication);
            ReflectionTestUtils.setField(savedStatus, "id", 1L);

            List<Status> statuses = List.of(savedStatus);

            when(applicationRepository.findById(1L)).thenReturn(Optional.of(existingApplication));

            when(companyRepository.findByName("Acme")).thenReturn(Optional.of(existingCompany));

            when(applicationRepository.save(org.mockito.ArgumentMatchers.any(Application.class))).thenReturn(savedApplication);

            when(statusRepository.findByApplicationId(savedApplication.getId())).thenReturn(statuses);

        // Act : when updateApplication is called with valid application and user id

            ApplicationResponseDto result = applicationService.updateApplication(1L, 1L, dto);

        // Assert :

            // Application id
            assertEquals(1L, result.getId());

            // Job title
            assertEquals("jobTitle", result.getJobTitle());

            // Company name
            assertEquals("Acme", result.getCompanyName());

            // Statuses
            assertEquals(1, result.getStatuses().size());
            assertEquals("A faire", result.getStatuses().get(0).getState());
    }

    // --- TEST : UPDATEAPPLICATION SHOULD FAIL WHEN APPLICATION ID IS NOT FOUND
    @Test
    void updateApplication_shouldFail_WhenApplicationIdIsNotFOund(){

        // Arrange data required : dto + mock applicationRepository
        ApplicationCreateDto dto = new ApplicationCreateDto(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            new java.sql.Date(System.currentTimeMillis()),
            "Acme", "Tech"
        );

        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and assert : when application is not valid, it should throw ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,() -> applicationService.updateApplication(1L, 1L, dto));
    }
}
