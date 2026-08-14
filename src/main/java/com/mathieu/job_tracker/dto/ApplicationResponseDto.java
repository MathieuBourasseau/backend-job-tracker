package com.mathieu.job_tracker.dto;

import java.sql.Date;
import java.util.List;

public class ApplicationResponseDto {
    
    // Data provides to the client
    private Long id;
    private String link;
    private String contact;
    private String job_title;
    private String location;
    private Integer salary;
    private String contract;
    private Date application_date;
    private Date application_re_submission_date;
    private Date application_re_submission_date_2;
    private Boolean interview;
    private String refusal_reason;
    private String companyName;
    private String companyActivity;
    private Long companyId;
    private List<StatusResponseDto> statuses;
    private Boolean aRelancer;

    // Constructors
    public ApplicationResponseDto() {}

    public ApplicationResponseDto(
        Long id, 
        String link, 
        String contact, 
        String job_title, 
        String location,
        Integer salary, 
        String contract, 
        Date application_date, 
        Date application_re_submission_date,
        Date application_re_submission_date_2, 
        Boolean interview, 
        String refusal_reason,
        String companyName, 
        String companyActivity, 
        Long companyId, 
        List<StatusResponseDto> statuses,
        Boolean aRelancer) {
        this.id = id;
        this.link = link;
        this.contact = contact;
        this.job_title = job_title;
        this.location = location;
        this.salary = salary;
        this.contract = contract;
        this.application_date = application_date;
        this.application_re_submission_date = application_re_submission_date;
        this.application_re_submission_date_2 = application_re_submission_date_2;
        this.interview = interview;
        this.refusal_reason = refusal_reason;
        this.companyName = companyName;
        this.companyActivity = companyActivity;
        this.companyId = companyId;
        this.statuses = statuses;
        this.aRelancer = aRelancer;
    }

    // Getters
    public Long getId() {
        return this.id;
    }

    public String getLink() {
        return this.link;
    }

    public String getContact() {
        return this.contact;
    }

    public String getJobTitle() {
        return this.job_title;
    }

    public String getLocation() {
        return this.location;
    }

    public Integer getSalary() {
        return this.salary;
    }

    public String getContract() {
        return this.contract;
    }

    public Date getApplicationDate() {
        return this.application_date;
    }

    public Date getApplicationReSubmissionDate() {
        return this.application_re_submission_date;
    }

    public Date getApplicationReSubmissionDate2() {
        return this.application_re_submission_date_2;
    }

    public Boolean getInterview() {
        return this.interview;
    }

    public String getRefusalReason() {
        return this.refusal_reason;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyActivity() {
        return this.companyActivity;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public List<StatusResponseDto> getStatuses() {
        return this.statuses;
    }

    public Boolean getARelancer(){
        return this.aRelancer;
    }

}
