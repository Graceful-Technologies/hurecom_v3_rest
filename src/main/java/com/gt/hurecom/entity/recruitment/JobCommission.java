package com.gt.hurecom.entity.recruitment;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_commissions")
public class JobCommission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "commission_type", nullable = false, length = 20)
    private String commissionType;

    @Column(name = "commission_value", nullable = false)
    private Double commissionValue;

    @Column(name = "guarantee_period_days", nullable = false)
    private Integer guaranteePeriodDays;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
