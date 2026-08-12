package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.exceptions.ResourceNotFoundException;
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
    
}
