package com.gt.hurecom.dto.admin;

import java.util.List;

public class TeamMemberRequest {

    private Long teamId;

    private List<Long> userIds;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

}
