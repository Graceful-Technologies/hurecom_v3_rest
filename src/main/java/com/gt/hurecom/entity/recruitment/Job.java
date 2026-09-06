package com.gt.hurecom.entity.recruitment;

import com.gt.hurecom.entity.common.BaseEntity;
import com.gt.hurecom.entity.master.Client;
import com.gt.hurecom.entity.master.ClientLocation;
import com.gt.hurecom.entity.master.ClientSpoc;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_location_id", nullable = false, updatable = false)
    private ClientLocation clientLocation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_spoc_id", nullable = false)
    private ClientSpoc clientSpoc;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<JobAssignment> assignments;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<JobCommission> commissions;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<JobAttachment> attachments;

    @Column(name = "job_code", nullable = false, length = 50)
    private String jobCode;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "open_positions", nullable = false)
    private Integer openPositions;

    @Column(name = "job_description", length = 5000)
    private String jobDescription;

    @Column(name = "min_experience")
    private Double minExperience;

    @Column(name = "max_experience")
    private Double maxExperience;

    @Column(name = "max_ctc")
    private Double maxCtc;

    @Column(name = "skills", length = 500)
    private String skills;

    @Column(name = "employment_type", length = 30)
    private String employmentType;

    @Column(name = "work_mode", length = 30)
    private String workMode;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientLocation getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(ClientLocation clientLocation) {
        this.clientLocation = clientLocation;
    }

    public ClientSpoc getClientSpoc() {
        return clientSpoc;
    }

    public void setClientSpoc(ClientSpoc clientSpoc) {
        this.clientSpoc = clientSpoc;
    }

    public List<JobAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<JobAssignment> assignments) {
        this.assignments = assignments;
    }

    public List<JobCommission> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<JobCommission> commissions) {
        this.commissions = commissions;
    }

    public List<JobAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<JobAttachment> attachments) {
        this.attachments = attachments;
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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
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
