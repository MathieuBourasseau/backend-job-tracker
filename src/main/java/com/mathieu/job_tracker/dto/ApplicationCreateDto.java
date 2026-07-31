package com.mathieu.job_tracker.dto;

import java.sql.Date;

public class ApplicationCreateDto {

    // Fields the client is allowed to provide
    private String link;
    private String contact;
    private String job_title;
    private String location;
    private Integer salary;
    private String contract;
    private Date application_date;
    private String companyName;
    private String companyActivity;

    // Constructors
    public ApplicationCreateDto() {}

    public ApplicationCreateDto(
        String link,
        String contact,
        String job_title,
        String location,
        Integer salary,
        String contract,
        Date application_date,
        String companyName,
        String companyActivity
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

}