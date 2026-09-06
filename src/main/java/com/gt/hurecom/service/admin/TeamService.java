package com.gt.hurecom.service.admin;

import com.gt.hurecom.dto.admin.TeamListResponse;
import com.gt.hurecom.dto.admin.TeamRequest;
import com.gt.hurecom.dto.admin.TeamResponse;

import java.util.List;

public interface TeamService {

    TeamResponse createTeam(TeamRequest request);

    TeamResponse updateTeam(Long id, TeamRequest request);

    TeamResponse getTeam(Long id);

    List<TeamListResponse> getTeams();

    List<TeamResponse> getTeamsNotAssignedToJob(Long jobId);
}
