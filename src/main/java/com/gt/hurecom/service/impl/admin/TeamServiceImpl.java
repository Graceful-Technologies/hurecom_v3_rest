package com.gt.hurecom.service.impl.admin;

import com.gt.hurecom.dto.admin.TeamListResponse;
import com.gt.hurecom.dto.admin.TeamRequest;
import com.gt.hurecom.dto.admin.TeamResponse;
import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.admin.TeamMapper;
import com.gt.hurecom.repository.admin.TeamMemberRepository;
import com.gt.hurecom.repository.admin.TeamRepository;
import com.gt.hurecom.service.admin.TeamService;
import com.gt.hurecom.utility.CommonUtils;
import com.gt.hurecom.utility.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    private final TeamMemberRepository teamMemberRepository;

    public TeamServiceImpl(TeamRepository teamRepository,
                           TeamMapper teamMapper,
                           TeamMemberRepository teamMemberRepository) {
        super();
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public TeamResponse createTeam(TeamRequest request) {
        log.debug("Service :: createTeam :: Entered");

        if (teamRepository.existsByNameIgnoreCase(request.getName())) {
            CommonUtils.throwBusinessException("Team name already exists.");
        }

        Team team = teamMapper.convertToEntity(request);
        Organization organization = SecurityUtils.getCurrentOrganization();
        team.setOrganization(organization);
        Team savedTeam = teamRepository.save(team);

        log.debug("Service :: createTeam :: Exited");
        return teamMapper.convertToResponse(savedTeam);
    }

    @Override
    public TeamResponse updateTeam(Long id, TeamRequest request) {
        log.debug("Service :: updateTeam :: Entered");

        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Team not found."));

        if (teamRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            CommonUtils.throwBusinessException("Team name already exists.");
        }

        teamMapper.updateFromRequest(request, existingTeam);
        Team savedTeam = teamRepository.save(existingTeam);

        log.debug("Service :: updateTeam :: Exited");
        return teamMapper.convertToResponse(savedTeam);
    }

    @Override
    public TeamResponse getTeam(Long id) {
        log.debug("Service :: getTeam :: Entered");

        Team team = teamRepository.getTeamById(id).orElseThrow(() -> new HurecomException("Team not found."));
        Long count = teamMemberRepository.countActiveMembers(id);

        TeamResponse response = teamMapper.convertToResponseWithAudit(team);
        response.setMembersCount(count);

        log.debug("Service :: getTeam :: Exited");
        return response;
    }

    @Override
    public List<TeamListResponse> getTeams() {
        log.debug("Service :: getTeams :: Entered");

        List<TeamListResponse> teams = teamRepository.getTeams();

        log.debug("Service :: getTeams :: Exited");
        return teams;
    }

    @Override
    public List<TeamResponse> getTeamsNotAssignedToJob(Long jobId) {
        log.debug("Service :: getTeamsNotAssignedToJob :: Entered");

        List<Team> teams = teamRepository.findTeamsNotAssignedToJob(jobId);
        List<TeamResponse> response = new ArrayList<>();

        teams.forEach(team -> {
            TeamResponse teamResponse = new TeamResponse();
            teamResponse.setId(team.getId());
            teamResponse.setName(team.getName());
            response.add(teamResponse);
        });

        log.debug("Service :: getTeamsNotAssignedToJob :: Exited");
        return response;
    }
}
