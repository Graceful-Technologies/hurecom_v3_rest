package com.gt.hurecom.entity.master;

import com.gt.hurecom.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "application_statuses", uniqueConstraints = { @UniqueConstraint(columnNames = { "stage_id", "code" }),
		@UniqueConstraint(columnNames = { "stage_id", "sequence" }) })
public class ApplicationStatus extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "stage_id", nullable = false)
	private ApplicationStage stage;

	@Column(nullable = false, length = 30)
	private String code;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false)
	private Integer sequence;

	@Column(name = "is_terminal", nullable = false)
	private boolean terminal;

	@Column(name = "is_followup_required", nullable = false)
	private boolean followupRequired;

	@Column(name = "is_active", nullable = false)
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicationStage getStage() {
		return stage;
	}

	public void setStage(ApplicationStage stage) {
		this.stage = stage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public boolean isFollowupRequired() {
		return followupRequired;
	}

	public void setFollowupRequired(boolean followupRequired) {
		this.followupRequired = followupRequired;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
