package com.example.akshay.myapplication.dao;

/**
 * Created by Akshay on 3/22/2017.
 */

public class PollEntity {

    private String pollName;
    private String pollStartDate;
    private String pollEndDate;

    public PollEntity(String pollName, String pollStartDate, String pollEndDate) {
        this.pollName = pollName;
        this.pollStartDate = pollStartDate;
        this.pollEndDate = pollEndDate;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getPollStartDate() {
        return pollStartDate;
    }

    public void setPollStartDate(String pollStartDate) {
        this.pollStartDate = pollStartDate;
    }

    public String getPollEndDate() {
        return pollEndDate;
    }

    public void setPollEndDate(String pollEndDate) {
        this.pollEndDate = pollEndDate;
    }
}
