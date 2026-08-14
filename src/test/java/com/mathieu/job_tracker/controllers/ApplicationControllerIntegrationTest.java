package com.mathieu.job_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Registers a user then logs in, and returns the JWT token to use in the Authorization header
    private String registerAndLogin(String email) throws Exception {

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "%s",
                      "password": "password123"
                    }
                    """.formatted(email)))
                .andExpect(status().isCreated());

        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "%s",
                      "password": "password123"
                    }
                    """.formatted(email)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        return objectMapper.readTree(responseBody).get("token").asText();
    }

    // --- TEST : GETUSERAPPLICATIONS SHOULD RETURN 403 WHEN NO TOKEN IS PROVIDED ---
    @Test
    void getUserApplications_shouldReturn403_whenNoTokenIsProvided() throws Exception {

        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isForbidden());
    }

    // --- TEST : CREATEAPPLICATION SHOULD RETURN 201 WHEN TOKEN IS VALID ---
    @Test
    void createApplication_shouldReturn201_whenTokenIsValid() throws Exception {

        String token = registerAndLogin("create-application@test.com");

        mockMvc.perform(post("/api/applications")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "link": "https://example.com/job",
                      "contact": "recruiter",
                      "jobTitle": "Developpeur backend",
                      "location": "Paris",
                      "salary": 40000,
                      "contract": "CDI",
                      "applicationDate": "2026-08-01",
                      "companyName": "Acme",
                      "companyActivity": "Tech"
                    }
                    """))
                .andExpect(status().isCreated());
    }

    // --- TEST : GETAPPLICATIONBYID SHOULD RETURN 404 WHEN APPLICATION BELONGS TO ANOTHER USER ---
    @Test
    void getApplicationById_shouldReturn404_whenApplicationBelongsToAnotherUser() throws Exception {

        // User A creates an application
        String tokenA = registerAndLogin("owner@test.com");

        MvcResult createResult = mockMvc.perform(post("/api/applications")
                .header("Authorization", "Bearer " + tokenA)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "link": "https://example.com/job",
                      "contact": "recruiter",
                      "jobTitle": "Developpeur backend",
                      "location": "Paris",
                      "salary": 40000,
                      "contract": "CDI",
                      "applicationDate": "2026-08-01",
                      "companyName": "Acme",
                      "companyActivity": "Tech"
                    }
                    """))
                .andExpect(status().isCreated())
                .andReturn();

        String createResponseBody = createResult.getResponse().getContentAsString();
        Long applicationId = objectMapper.readTree(createResponseBody).get("id").asLong();

        // User B tries to access user A's application
        String tokenB = registerAndLogin("intruder@test.com");

        mockMvc.perform(get("/api/applications/" + applicationId)
                .header("Authorization", "Bearer " + tokenB))
                .andExpect(status().isNotFound());
    }

}
