package com.mathieu.job_tracker.models;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    // --- TEST : NEEDSFOLLOWUP SHOULD RETURN FALSE WHEN STATUS IS NOT EN_COURS ---
    @Test
    void needsFollowUp_shouldReturnFalse_whenStatusIsNotEnCours(){

        // Arrange data required : application with no relevant dates, status different from EN_COURS
        Application application = new Application(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            Date.valueOf(LocalDate.now().minusDays(10)),
            null, null, false, null,
            null, null
        );

        // Act : call needsFollowUp with A_FAIRE
        Boolean result = application.needsFollowUp(StatusState.A_FAIRE);

        // Assert : no follow-up needed since the application is not EN_COURS
        assertFalse(result);
    }

    // --- TEST : NEEDSFOLLOWUP SHOULD RETURN FALSE WHEN BOTH RELANCES ARE ALREADY DONE ---
    @Test
    void needsFollowUp_shouldReturnFalse_whenBothRelancesAreAlreadyDone(){

        // Arrange data required : application with both re-submission dates set, status EN_COURS
        Application application = new Application(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            Date.valueOf(LocalDate.now().minusDays(30)),
            Date.valueOf(LocalDate.now().minusDays(20)),
            Date.valueOf(LocalDate.now().minusDays(15)),
            false, null,
            null, null
        );

        // Act : call needsFollowUp with EN_COURS
        Boolean result = application.needsFollowUp(StatusState.EN_COURS);

        // Assert : no follow-up needed, the 2 allowed relances are already done
        assertFalse(result);
    }

    // --- TEST : NEEDSFOLLOWUP SHOULD RETURN TRUE WHEN 7 DAYS HAVE PASSED SINCE THE INITIAL APPLICATION ---
    @Test
    void needsFollowUp_shouldReturnTrue_whenSevenDaysHavePassedSinceApplicationDate(){

        // Arrange data required : application sent exactly 7 days ago, no relance yet, status EN_COURS
        Application application = new Application(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            Date.valueOf(LocalDate.now().minusDays(7)),
            null, null, false, null,
            null, null
        );

        // Act : call needsFollowUp with EN_COURS
        Boolean result = application.needsFollowUp(StatusState.EN_COURS);

        // Assert : 7 days have passed since the initial application, a relance is needed
        assertTrue(result);
    }

    // --- TEST : NEEDSFOLLOWUP SHOULD RETURN FALSE WHEN LESS THAN 7 DAYS HAVE PASSED SINCE THE INITIAL APPLICATION ---
    @Test
    void needsFollowUp_shouldReturnFalse_whenLessThanSevenDaysHavePassedSinceApplicationDate(){

        // Arrange data required : application sent 3 days ago, no relance yet, status EN_COURS
        Application application = new Application(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            Date.valueOf(LocalDate.now().minusDays(3)),
            null, null, false, null,
            null, null
        );

        // Act : call needsFollowUp with EN_COURS
        Boolean result = application.needsFollowUp(StatusState.EN_COURS);

        // Assert : less than 7 days have passed, no relance needed yet
        assertFalse(result);
    }

    // --- TEST : NEEDSFOLLOWUP SHOULD RETURN TRUE WHEN 7 DAYS HAVE PASSED SINCE THE FIRST RELANCE ---
    @Test
    void needsFollowUp_shouldReturnTrue_whenSevenDaysHavePassedSinceFirstRelance(){

        // Arrange data required : first relance sent 10 days ago, no second relance, status EN_COURS
        Application application = new Application(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            Date.valueOf(LocalDate.now().minusDays(30)),
            Date.valueOf(LocalDate.now().minusDays(10)),
            null, false, null,
            null, null
        );

        // Act : call needsFollowUp with EN_COURS
        Boolean result = application.needsFollowUp(StatusState.EN_COURS);

        // Assert : the reference date should be the first relance, not the initial application date
        assertTrue(result);
    }

    // --- TEST : NEEDSFOLLOWUP SHOULD RETURN FALSE WHEN LESS THAN 7 DAYS HAVE PASSED SINCE THE FIRST RELANCE ---
    @Test
    void needsFollowUp_shouldReturnFalse_whenLessThanSevenDaysHavePassedSinceFirstRelance(){

        // Arrange data required : first relance sent 2 days ago, no second relance, status EN_COURS
        Application application = new Application(
            "link", "contact", "jobTitle", "location", 1000, "CDI",
            Date.valueOf(LocalDate.now().minusDays(30)),
            Date.valueOf(LocalDate.now().minusDays(2)),
            null, false, null,
            null, null
        );

        // Act : call needsFollowUp with EN_COURS
        Boolean result = application.needsFollowUp(StatusState.EN_COURS);

        // Assert : less than 7 days since the first relance, no follow-up needed yet
        assertFalse(result);
    }
}
