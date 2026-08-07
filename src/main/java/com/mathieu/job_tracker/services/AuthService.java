package com.mathieu.job_tracker.services;

import com.mathieu.job_tracker.dto.LoginCreateDto;
import com.mathieu.job_tracker.dto.LoginResponseDto;
import com.mathieu.job_tracker.exceptions.InvalidCredentialsException;
import com.mathieu.job_tracker.models.User;
import com.mathieu.job_tracker.repositories.UserRepository;
import com.mathieu.job_tracker.security.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    // Dependencies needed to check user data, hash password and create a token
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    
    // Constructor
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // --- LOGIN METHOD --- 
    public LoginResponseDto login(LoginCreateDto dto) {

        // Get user data connection
        String email = dto.getEmail();
        String password = dto.getPassword();

        // Checking user data connection validity
        // If email is wrong
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Email ou mot de passe incorrect"));

        // If password is wrong
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException("Email ou mot de passe inccorect");
        }

        




        // Vérifier s'ils correspondent en base de données si l'un ou l'autre est faux on crée une new InvalidCredentialsException.java avec le message approprié
        // Si ca correspond on créé le token avec le secret pet la signature
        // Renvoyer le token vers le front 
    }
}
