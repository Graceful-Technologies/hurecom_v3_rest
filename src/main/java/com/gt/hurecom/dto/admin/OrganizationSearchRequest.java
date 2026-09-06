package com.gt.hurecom.dto.admin;

import com.gt.hurecom.dto.common.PageRequest;

public class OrganizationSearchRequest extends PageRequest {

	private String name;

	private Boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
