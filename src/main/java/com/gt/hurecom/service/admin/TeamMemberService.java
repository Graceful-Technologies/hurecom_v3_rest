package com.gt.hurecom.service.admin;

import com.gt.hurecom.dto.admin.TeamMemberRequest;
import com.gt.hurecom.dto.admin.TeamMemberResponse;
import com.gt.hurecom.dto.admin.TeamResponse;
import com.gt.hurecom.dto.admin.UserResponse;

import java.util.List;

public interface TeamMemberService {

    void createTeamMembers(TeamMemberRequest request);

    void removeTeamMember(Long id);

    List<TeamMemberResponse> getTeamMembers(Long teamId);

    List<UserResponse> getActiveUsersByTeam(Long teamId);

    List<TeamResponse> getActiveTeamsByUser(Long userId);

    boolean isUserPartOfTeam(Long userId, Long teamId);

}
