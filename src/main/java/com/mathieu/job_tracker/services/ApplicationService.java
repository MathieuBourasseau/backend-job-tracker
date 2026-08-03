package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.ApplicationCreateDto;
import com.mathieu.job_tracker.dto.ApplicationResponseDto;
import com.mathieu.job_tracker.models.Application;
import com.mathieu.job_tracker.models.Company;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.ApplicationRepository;
import com.mathieu.job_tracker.repositories.CompanyRepository;
import com.mathieu.job_tracker.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    
    // Repository required to get access to methods needed
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    // ApplicationService needs ApplicationRepository to work
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository, CompanyRepository companyRepository){
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    // ApplicationService methods
    public ApplicationResponseDto createApplication(ApplicationCreateDto dto, Long userId){
        
        // Find user in DB with the id
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Check if the company is already existing || Create the company if not found
        Company company = companyRepository.findByName(dto.getCompanyName())
            .orElseGet(() -> companyRepository.save(new Company(dto.getCompanyName(), dto.getCompanyActivity())));

    }

    
}
