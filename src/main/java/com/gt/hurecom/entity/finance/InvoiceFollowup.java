package com.gt.hurecom.entity.finance;

import java.time.LocalDateTime;

import com.gt.hurecom.entity.admin.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_followups")
public class InvoiceFollowup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@Column(name = "followup_date", nullable = false)
	private LocalDateTime followupDate;

	@Column(name = "followup_type", length = 30)
	private String followupType;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "next_followup_date", nullable = false)
	private LocalDateTime nextFollowupDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private User createdBy;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public LocalDateTime getFollowupDate() {
		return followupDate;
	}

	public void setFollowupDate(LocalDateTime followupDate) {
		this.followupDate = followupDate;
	}

	public String getFollowupType() {
		return followupType;
	}

	public void setFollowupType(String followupType) {
		this.followupType = followupType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getNextFollowupDate() {
		return nextFollowupDate;
	}

	public void setNextFollowupDate(LocalDateTime nextFollowupDate) {
		this.nextFollowupDate = nextFollowupDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
