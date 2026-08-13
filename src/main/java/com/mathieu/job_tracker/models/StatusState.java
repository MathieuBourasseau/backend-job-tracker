package com.mathieu.job_tracker.models;

public enum StatusState {

    // Application created, not yet actually sent
    A_FAIRE,

    // Application sent, waiting for a response
    EN_COURS,

    // Application refused or with no definitive response
    REFUS
}
