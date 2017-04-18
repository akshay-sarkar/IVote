package com.example.akshay.myapplication.dao;

/**
 * Created by Akshay on 3/22/2017.
 */

public class PollEntity {

    private int pollId;
    private String pollName;
    private String pollStartDate;
    private String pollEndDate;
    private String isPollActive;

    public PollEntity(int pollId, String pollName, String pollStartDate, String pollEndDate, String pollActive) {
        this.pollId = pollId;
        this.pollName = pollName;
        this.pollStartDate = pollStartDate;
        this.pollEndDate = pollEndDate;
        this.isPollActive = pollActive;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
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

    public String getIsPollActive() {
        return isPollActive;
    }

    public void setIsPollActive(String isPollActive) {
        this.isPollActive = isPollActive;
    }
}
