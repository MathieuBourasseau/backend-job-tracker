package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.UserCreateDto;
import com.mathieu.job_tracker.dto.UserResponseDto;
import com.mathieu.job_tracker.exceptions.EmailAlreadyExistsException;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Repository required to get access to its methods
    private final UserRepository userRepository;

    // Required to hash password
    private final PasswordEncoder passwordEncoder;


    // UserServices needs the repository to use the business logic
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Methods of this service
    public UserResponseDto createUser(UserCreateDto dto) {

        // Condition to check if the mail already exists before creating user
        String email = dto.getEmail();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Ce mail existe déjà.");
        }

        // If conditions are valid we can create and save the new user in DB
        // Password is hashed before saving it in DB
        User newUser = new User(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(newUser);

        UserResponseDto response = new UserResponseDto(savedUser.getEmail(), savedUser.getId());
        
        return response;
        
    }
}
