package com.example.akshay.myapplication.dao;

import java.io.Serializable;

/**
 * Created by Akshay on 4/13/2017.
 */

public class CandidateEntity implements Serializable{

    int CandidateID;
    String FirstName;
    String LastName;
    String UTAEmailID;
    String DOB;
    String Gender;
    String Department;
    String Qualities;
    String Interests;
    String StudentOrganization;
    String CommunityServiceHours;

        public CandidateEntity(int candidateID, String firstName, String lastName, String UTAEmailID,
                               String DOB, String gender, String department, String qualities,
                               String interests, String studentOrganization, String communityServiceHours) {
        this.CandidateID = candidateID;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.UTAEmailID = UTAEmailID;
        this.DOB = DOB;
        this.Gender = gender;
        this.Department = department;
        this.Qualities = qualities;
        this.Interests = interests;
        this.StudentOrganization = studentOrganization;
        this.CommunityServiceHours = communityServiceHours;
    }

    public int getCandidateID() {
        return CandidateID;
    }

    public void setCandidateID(int candidateID) {
        CandidateID = candidateID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUTAEmailID() {
        return UTAEmailID;
    }

    public void setUTAEmailID(String UTAEmailID) {
        this.UTAEmailID = UTAEmailID;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getQualities() {
        return Qualities;
    }

    public void setQualities(String qualities) {
        Qualities = qualities;
    }

    public String getInterests() {
        return Interests;
    }

    public void setInterests(String interests) {
        Interests = interests;
    }

    public String getStudentOrganization() {
        return StudentOrganization;
    }

    public void setStudentOrganization(String studentOrganization) {
        StudentOrganization = studentOrganization;
    }

    public String getCommunityServiceHours() {
        return CommunityServiceHours;
    }

    public void setCommunityServiceHours(String communityServiceHours) {
        CommunityServiceHours = communityServiceHours;
    }
}
