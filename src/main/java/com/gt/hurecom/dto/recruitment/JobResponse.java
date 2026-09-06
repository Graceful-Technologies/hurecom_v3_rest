package com.gt.hurecom.dto.recruitment;

import com.gt.hurecom.dto.common.BaseResponse;

import java.util.List;

public class JobResponse extends BaseResponse {

    private Long id;

    private Long organizationId;

    private String organizationName;

    private Long clientId;

    private String clientName;

    private Long clientLocationId;

    private String clientLocation;

    private Long clientSpocId;

    private String clientSpocName;

    private String jobCode;

    private String title;

    private Integer openPositions;

    private String jobDescription;

    private Double minExperience;

    private Double maxExperience;

    private Double maxCtc;

    private List<String> skills;

    private String employmentType;

    private String employmentTypeValue;

    private String workMode;

    private String workModeValue;

    private String status;

    private String statusValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(Long clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public Long getClientSpocId() {
        return clientSpocId;
    }

    public void setClientSpocId(Long clientSpocId) {
        this.clientSpocId = clientSpocId;
    }

    public String getClientSpocName() {
        return clientSpocName;
    }

    public void setClientSpocName(String clientSpocName) {
        this.clientSpocName = clientSpocName;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
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

    public String getEmploymentTypeValue() {
        return employmentTypeValue;
    }

    public void setEmploymentTypeValue(String employmentTypeValue) {
        this.employmentTypeValue = employmentTypeValue;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getWorkModeValue() {
        return workModeValue;
    }

    public void setWorkModeValue(String workModeValue) {
        this.workModeValue = workModeValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
