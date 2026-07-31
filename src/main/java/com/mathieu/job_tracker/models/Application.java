package com.mathieu.job_tracker.models;

import java.sql.Date;

import jakarta.persistence.*;

// Create Application entity

@Entity
@Table(name = "applications")
public class Application {

    // Id is auto-incremented with the DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the job posting, optional
    @Column(length = 500)
    private String link;

    // Recruiter or contact name, optional
    @Column(length = 255)
    private String contact;

    // Job title is obligatory
    @Column(nullable = false, length = 255)
    private String job_title;

    // Location is obligatory
    @Column(nullable = false, length = 255)
    private String location;

    // Salary is optional
    @Column
    private Integer salary;

    // Contract type is obligatory
    @Column(nullable = false, length = 50)
    private String contract;

    // Initial application date is obligatory
    @Column(nullable = false)
    private Date application_date;

    // First follow-up date, optional
    @Column
    private Date application_re_submission_date;

    // Second follow-up date, optional
    @Column
    private Date application_re_submission_date_2;

    // Interview obtained, defaults to false
    @Column(nullable = false)
    private Boolean interview = false;

    // Refusal reason, optional, relevant only if the application was refused
    @Column(length = 255)
    private String refusal_reason;

    // Foreign Key for user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Foreign Key for company_id
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Application() {
    }

    // Constructor
    public Application(String link, String contact, String job_title, String location, Integer salary,
            String contract, Date application_date, Date application_re_submission_date,
            Date application_re_submission_date_2, Boolean interview, String refusal_reason,
            User user, Company company) {
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
        this.user = user;
        this.company = company;
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJobTitle() {
        return this.job_title;
    }

    public void setJobTitle(String job_title) {
        this.job_title = job_title;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSalary() {
        return this.salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getContract() {
        return this.contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Date getApplicationDate() {
        return this.application_date;
    }

    public void setApplicationDate(Date application_date) {
        this.application_date = application_date;
    }

    public Date getApplicationReSubmissionDate() {
        return this.application_re_submission_date;
    }

    public void setApplicationReSubmissionDate(Date application_re_submission_date) {
        this.application_re_submission_date = application_re_submission_date;
    }

    public Date getApplicationReSubmissionDate2() {
        return this.application_re_submission_date_2;
    }

    public void setApplicationReSubmissionDate2(Date application_re_submission_date_2) {
        this.application_re_submission_date_2 = application_re_submission_date_2;
    }

    public Boolean getInterview() {
        return this.interview;
    }

    public void setInterview(Boolean interview) {
        this.interview = interview;
    }

    public String getRefusalReason() {
        return this.refusal_reason;
    }

    public void setRefusalReason(String refusal_reason) {
        this.refusal_reason = refusal_reason;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
