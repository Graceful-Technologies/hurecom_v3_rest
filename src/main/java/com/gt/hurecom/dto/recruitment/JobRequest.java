package com.gt.hurecom.dto.recruitment;

import java.util.List;

public class JobRequest {

    private Long organizationId;

    private Long clientId;

    private Long clientLocationId;

    private Long clientSpocId;

    private String title;

    private Integer openPositions;

    private String jobDescription;

    private Double minExperience;

    private Double maxExperience;

    private Double maxCtc;

    private List<String> skills;

    private String employmentType;

    private String workMode;

    private String status;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(Long clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public Long getClientSpocId() {
        return clientSpocId;
    }

    public void setClientSpocId(Long clientSpocId) {
        this.clientSpocId = clientSpocId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOpenPositions() {
        return openPositions;
    }

    public void setOpenPositions(Integer openPositions) {
        this.openPositions = openPositions;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Double getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(Double minExperience) {
        this.minExperience = minExperience;
    }

    public Double getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(Double maxExperience) {
        this.maxExperience = maxExperience;
    }

    public Double getMaxCtc() {
        return maxCtc;
    }

    public void setMaxCtc(Double maxCtc) {
        this.maxCtc = maxCtc;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
