package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.LoginCreateDto;
import com.mathieu.job_tracker.dto.LoginResponseDto;
import com.mathieu.job_tracker.exceptions.InvalidCredentialsException;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.UserRepository;
import com.mathieu.job_tracker.security.JwtUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    
    // Mocks needed to create the AuthServiceTest
    @Mock
    private UserRepository userRepository;

    @Mock 
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    // --- LOGIN METHOD TEST --- 
    @Test
    void login_shouldSucceed_WhenEmailAndPasswordAreValid(){

        // Arrange the data required to do the test 

            // Create dto
            LoginCreateDto dto = new LoginCreateDto("test@test.com", "password123");

            // Create user already existing
            User existingUser = new User("test@test.com", "hashedPassword");

            // Simulate that a user is existing
            when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUser));

            // Simulate hashed password
            when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);

            // Build a manually forced id
            ReflectionTestUtils.setField(existingUser, "id", 1L);

            // Generate a token when email, password and id are valid
            when(jwtUtil.generateToken(1L)).thenReturn("fakeToken");

        // Act : by using the login method

            LoginResponseDto result = authService.login(dto);

        // Assert : check that the returned dto matches what we excpeted

            // Token
            assertEquals("fakeToken", result.getToken());

            // Email
            assertEquals("test@test.com", result.getEmail());

            // Id
             assertEquals(1L, result.getId());
    }

    // --- LOGIN FAIL TEST IN CASE OF WRONG EMAIL --- 
    @Test
    void login_shouldFail_whenEmailIsNotFound(){

        // Arrange required data 

            // LoginCreateDto
            LoginCreateDto dto = new LoginCreateDto("test@test.com", "password123");

            // Simulate that the email does not exist in DB
            when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());


        // Act and assert

            // Calling login(dto) should throw an InvalidCredentialsException when no mail is found
            assertThrows(InvalidCredentialsException.class, () -> authService.login(dto));
    }

    // --- LOGIN FAIL TEST IN CASE OF WRONG PASSWORD --- 
    @Test
    void login_shouldFail_WhenPasswordIsNotFound(){
        
         // Assert data required : createDto, existing user, email valid, wrong password

            // LoginCreateDto
            LoginCreateDto dto = new LoginCreateDto("test@test.com", "password123");

            // Existing user
            User existingUser = new User("test@test.com", "hashedPassword");

            // Simulate a valid email
            when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUser));

            // Hashed password
            when(passwordEncoder.matches("password123","hashedPassword")).thenReturn(false);

            // Act and assert

                // Calling login(dto) should throw an InvalidCredentialException
                assertThrows(InvalidCredentialsException.class, () -> authService.login(dto));
    }
}
