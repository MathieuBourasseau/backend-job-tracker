package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.StatusCreateDto;
import com.mathieu.job_tracker.dto.StatusResponseDto;
import com.mathieu.job_tracker.exceptions.ResourceNotFoundException;
import com.mathieu.job_tracker.models.Status;
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
        
}
