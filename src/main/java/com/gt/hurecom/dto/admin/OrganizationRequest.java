package com.gt.hurecom.dto.admin;

public class OrganizationRequest {

	private String name;

	private Long parentOrganizationId;

	private boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(Long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
