package com.mathieu.job_tracker.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // --- TEST : GETUSERAPPLICATIONS SHOULD RETURN 403 WHEN NO TOKEN IS PROVIDED ---
    @Test
    void getUserApplications_shouldReturn403_whenNoTokenIsProvided() throws Exception {

        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isForbidden());
    }

}
