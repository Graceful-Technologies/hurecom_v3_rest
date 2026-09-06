package com.gt.hurecom.dto.master;

import com.gt.hurecom.dto.common.PageRequest;

public class ClientSearchRequest extends PageRequest {

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
