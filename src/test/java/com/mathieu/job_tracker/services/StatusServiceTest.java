package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.exceptions.ResourceNotFoundException;
import com.mathieu.job_tracker.models.Application;
import com.mathieu.job_tracker.models.Company;
import com.mathieu.job_tracker.models.Status;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.ApplicationRepository;
import com.mathieu.job_tracker.repositories.StatusRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {

    // Mocks needed to create StatusServiceTest

    // ApplicationRepository
    @Mock
    private ApplicationRepository applicationRepository;

    // StatusRepository
    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusService statusService;

    // --- CREATESTATUS METHOD TEST ---
    @Test
    void createStatus_shouldSucceed_WhenApplicationAndUserIdAreValid() {

        // Arrange data required : StatusCreateDto, existing user, existing company,
        
            // StatusCreateDto
            StatusCreateDto dto = new StatusCreateDto("A faire", 1L);

            // Existing User
            User user = new User("test@test.com", "hashedPassword");

            ReflectionTestUtils.setField(user, "id", 1L);

            // Existing company
            Company company = new Company("Acme", "Tech");

            // Existing application
            Application existingApplication = new Application(
                    "link", "contact", "jobTitle", "location", 1000, "CDI",
                    new java.sql.Date(System.currentTimeMillis()),
                    null, null, false, null,
                    user, company);
        
            ReflectionTestUtils.setField(existingApplication, "id", 1L);

            // Simulate that an application is existing
            when(applicationRepository.findById(1L)).thenReturn(Optional.of(existingApplication));

            // Simulate the status history read back after the new status is saved
            Status savedStatus = new Status("A faire", new java.sql.Timestamp(System.currentTimeMillis()), existingApplication);
            ReflectionTestUtils.setField(savedStatus, "id", 1L);
            
            when(statusRepository.findByApplicationId(1L)).thenReturn(java.util.List.of(savedStatus));

        // Act : when createStatus is used

            // Call createStatus method
            ApplicationResponseDto result = statusService.createStatus(1L, dto);

        // Assert : expected all the data of application with the list of statuses

            // Application data
            assertEquals(1L, result.getId());
            assertEquals("jobTitle", result.getJobTitle());
            assertEquals("Acme", result.getCompanyName());

            // Status history
            assertEquals(1, result.getStatuses().size());
            assertEquals("A faire", result.getStatuses().get(0).getState());
    }

}
