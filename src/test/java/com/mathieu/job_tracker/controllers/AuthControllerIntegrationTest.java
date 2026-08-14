package com.mathieu.job_tracker.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // --- TEST : LOGIN SHOULD RETURN 200 AND A TOKEN WHEN CREDENTIALS ARE VALID ---
    @Test
    void login_shouldReturn200AndToken_whenCredentialsAreValid() throws Exception {

        // Register the user first
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "login-success@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isCreated());

        // Login with the same credentials
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "login-success@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    // --- TEST : LOGIN SHOULD RETURN 401 WHEN PASSWORD IS WRONG ---
    @Test
    void login_shouldReturn401_whenPasswordIsWrong() throws Exception {

        // Register the user first
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "login-wrong-password@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isCreated());

        // Login with a wrong password
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "login-wrong-password@test.com",
                      "password": "wrongPassword"
                    }
                    """))
                .andExpect(status().isUnauthorized());
    }

    // --- TEST : LOGIN SHOULD RETURN 401 WHEN EMAIL IS NOT FOUND ---
    @Test
    void login_shouldReturn401_whenEmailIsNotFound() throws Exception {

        // Login with an email that was never registered
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "does-not-exist@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isUnauthorized());
    }

}
