package com.mathieu.job_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Starts the whole Spring application context for this test class (heavier than a Mockito unit test)
@SpringBootTest
class JobTrackerApplicationTests {

	// Empty on purpose: just starting the application context without error is the actual check here
	@Test
	void contextLoads() {
	}

}
