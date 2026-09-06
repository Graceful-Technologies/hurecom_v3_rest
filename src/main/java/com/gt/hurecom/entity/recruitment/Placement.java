package com.gt.hurecom.entity.recruitment;

import java.time.LocalDate;

import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.entity.common.BaseEntity;
import com.gt.hurecom.entity.master.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "placements", uniqueConstraints = { @UniqueConstraint(columnNames = { "application_id" }) })
public class Placement extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "application_id")
	private Application application;

	@ManyToOne(optional = false)
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "job_id")
	private Job job;

	@ManyToOne(optional = false)
	@JoinColumn(name = "client_id")
	private Client client;

	@ManyToOne(optional = false)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@ManyToOne(optional = false)
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User recruiter;

	@Column(name = "joined_date", nullable = false)
	private LocalDate joinedDate;

	@Column(name = "offered_ctc")
	private Double offeredCtc;

	@Column(name = "commission_type", length = 20)
	private String commissionType;

	@Column(name = "commission_value")
	private Double commissionValue;

	@Column(name = "billing_amount")
	private Double billingAmount;

	@Column(name = "guarantee_end_date")
	private LocalDate guaranteeEndDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public User getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}

	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}

	public Double getOfferedCtc() {
		return offeredCtc;
	}

	public void setOfferedCtc(Double offeredCtc) {
		this.offeredCtc = offeredCtc;
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

	public Double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}

	public LocalDate getGuaranteeEndDate() {
		return guaranteeEndDate;
	}

	public void setGuaranteeEndDate(LocalDate guaranteeEndDate) {
		this.guaranteeEndDate = guaranteeEndDate;
	}

}