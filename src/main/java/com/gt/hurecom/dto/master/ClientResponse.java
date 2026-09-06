package com.gt.hurecom.dto.master;

import com.gt.hurecom.dto.common.BaseResponse;

public class ClientResponse extends BaseResponse {

    private Long id;

    private String name;

    private boolean active;

    private Long locationCount;

    private Long spocCount;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(Long locationCount) {
        this.locationCount = locationCount;
    }

    public Long getSpocCount() {
        return spocCount;
    }

    public void setSpocCount(Long spocCount) {
        this.spocCount = spocCount;
    }
}
