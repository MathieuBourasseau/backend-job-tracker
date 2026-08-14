package com.mathieu.job_tracker.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // --- TEST : CREATEUSER SHOULD RETURN 201 WHEN EMAIL IS NOT TAKEN ---
    @Test
    void createUser_shouldReturn201_whenEmailIsNotTaken() throws Exception {

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "test@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isCreated());
    }

    // --- TEST : CREATEUSER SHOULD RETURN 409 WHEN EMAIL IS ALREADY TAKEN ---
    @Test
    void createUser_shouldReturn409_whenEmailIsAlreadyTaken() throws Exception {

        // First registration : should succeed
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "duplicate@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isCreated());

        // Second registration with the same email : should fail
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "duplicate@test.com",
                      "password": "password123"
                    }
                    """))
                .andExpect(status().isConflict());
    }

}
