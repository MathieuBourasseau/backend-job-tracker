package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.UserCreateDto;
import com.mathieu.job_tracker.dto.UserResponseDto;
import com.mathieu.job_tracker.exceptions.EmailAlreadyExistsException;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSucceed_whenEmailIsNotTaken() {
        // Arrange: build the input data and program what each mock should return
        UserCreateDto dto = new UserCreateDto("test@test.com", "password123");

        // Simulate "this email does not exist yet in the database"
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        // Simulate hashing, without doing any real hashing
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        // Build the User we want save() to return, with a manually forced id (no setter exists for it)
        User savedUser = new User("test@test.com", "hashedPassword");
        ReflectionTestUtils.setField(savedUser, "id", 1L);
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(savedUser);

        // Act: call the real method being tested
        UserResponseDto result = userService.createUser(dto);

        // Assert: check that the returned DTO matches what we expected
        assertEquals("test@test.com", result.getEmail());
        assertEquals(1L, result.getId());
    }
}
