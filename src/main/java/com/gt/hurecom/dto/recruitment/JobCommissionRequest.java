package com.gt.hurecom.dto.recruitment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class JobCommissionRequest {

    @NotNull(message = "Job ID is required.")
    private Long jobId;

    @NotBlank(message = "Commission type is required.")
    private String commissionType;

    @NotNull(message = "Commission value is required.")
    @Positive(message = "Commission value must be greater than 0.")
    private Double commissionValue;

    @NotNull(message = "Guarantee period is required.")
    @PositiveOrZero(message = "Guarantee period must be 0 or more.")
    private Integer guaranteePeriodDays;

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
}
