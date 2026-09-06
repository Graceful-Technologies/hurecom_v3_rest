package com.gt.hurecom.dto.admin;

import com.gt.hurecom.dto.common.BaseResponse;

public class OrganizationResponse extends BaseResponse {

	private Long id;

	private String name;

	private Long parentOrganizationId;

	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
