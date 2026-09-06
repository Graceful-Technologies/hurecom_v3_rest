package com.gt.hurecom.dto.recruitment;

import com.gt.hurecom.dto.common.PageRequest;

public class CandidateSearchRequest extends PageRequest {

    private String name;

    private String email;

    private String mobileNumber;

    private String skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
