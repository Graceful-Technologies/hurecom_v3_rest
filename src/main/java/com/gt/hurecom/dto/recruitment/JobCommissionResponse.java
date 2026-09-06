package com.gt.hurecom.dto.recruitment;

import java.time.LocalDate;

public class JobCommissionResponse {

    private Long id;

    private Long jobId;

    private String commissionType;

    private String commissionTypeValue;

    private Double commissionValue;

    private Integer guaranteePeriodDays;

    private LocalDate startDate;

    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }
    
    public String getCommissionTypeValue() {
        return commissionTypeValue;
    }

    public void setCommissionTypeValue(String commissionTypeValue) {
        this.commissionTypeValue = commissionTypeValue;
    }

    public Double getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(Double commissionValue) {
        this.commissionValue = commissionValue;
    }

    public Integer getGuaranteePeriodDays() {
        return guaranteePeriodDays;
    }

    public void setGuaranteePeriodDays(Integer guaranteePeriodDays) {
        this.guaranteePeriodDays = guaranteePeriodDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
