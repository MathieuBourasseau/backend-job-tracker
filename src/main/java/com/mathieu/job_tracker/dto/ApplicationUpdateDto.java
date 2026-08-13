package com.mathieu.job_tracker.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApplicationUpdateDto {

    // Fields the client is allowed to provide
    private String link;
    private String contact;

    @NotBlank(message = "Titre de candidature requis.")
    private String job_title;

    @NotBlank(message = "Localisation requise.")
    private String location;

    private Integer salary;

    @NotBlank(message = "Type de contrat requis.")
    private String contract;

    @NotNull(message = "Une date est requise.")
    private Date application_date;

    @NotBlank(message = "Nom d'entreprise requis")
    private String companyName;

    @NotBlank(message = "Activité de l'entreprise requis")
    private String companyActivity;

    private Date application_re_submission_date;
    private Date application_re_submission_date_2;
    private Boolean interview;
    private String refusal_reason;

    // Constructors
    public ApplicationUpdateDto() {}

    public ApplicationUpdateDto(
        String link,
        String contact,
        String job_title,
        String location,
        Integer salary,
        String contract,
        Date application_date,
        String companyName,
        String companyActivity,
        Date application_re_submission_date,
        Date application_re_submission_date_2,
        Boolean interview,
        String refusal_reason
    ){
        this.link = link;
        this.contact = contact;
        this.job_title = job_title;
        this.location = location;
        this.salary = salary;
        this.contract = contract;
        this.application_date = application_date;
        this.companyName = companyName;
        this.companyActivity = companyActivity;
        this.application_re_submission_date = application_re_submission_date;
        this.application_re_submission_date_2 = application_re_submission_date_2;
        this.interview = interview;
        this.refusal_reason = refusal_reason;
    }

    // Getters and setters
    public String getLink(){
        return this.link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getContact(){
        return this.contact;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    public String getJobTitle(){
        return this.job_title;
    }

    public void setJobTitle(String job_title){
        this.job_title = job_title;
    }

    public String getLocation(){
        return this.location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public Integer getSalary(){
        return this.salary;
    }

    public void setSalary(Integer salary){
        this.salary = salary;
    }

    public String getContract(){
        return this.contract;
    }

    public void setContract(String contract){
        this.contract = contract;
    }

    public Date getApplicationDate(){
        return this.application_date;
    }

    public void setApplicationDate(Date application_date){
        this.application_date = application_date;
    }

    public String getCompanyName(){
        return this.companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyActivity(){
        return this.companyActivity;
    }

    public void setCompanyActivity(String companyActivity){
        this.companyActivity = companyActivity;
    }

    public Date getApplicationReSubmissionDate(){
        return this.application_re_submission_date;
    }

    public void setApplicationReSubmissionDate(Date application_re_submission_date){
        this.application_re_submission_date = application_re_submission_date;
    }

    public Date getApplicationReSubmissionDate2(){
        return this.application_re_submission_date_2;
    }

    public void setApplicationReSubmissionDate2(Date application_re_submission_date_2){
        this.application_re_submission_date_2 = application_re_submission_date_2;
    }

    public Boolean getInterview(){
        return this.interview;
    }

    public void setInterview(Boolean interview){
        this.interview = interview;
    }

    public String getRefusalReason(){
        return this.refusal_reason;
    }

    public void setRefusalReason(String refusal_reason){
        this.refusal_reason = refusal_reason;
    }

}
