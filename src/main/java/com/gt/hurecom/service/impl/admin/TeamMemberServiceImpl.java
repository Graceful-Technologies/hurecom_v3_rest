package com.gt.hurecom.service.impl.admin;

import com.gt.hurecom.dto.admin.TeamMemberRequest;
import com.gt.hurecom.dto.admin.TeamMemberResponse;
import com.gt.hurecom.dto.admin.TeamResponse;
import com.gt.hurecom.dto.admin.UserResponse;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.admin.TeamMember;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.admin.TeamMapper;
import com.gt.hurecom.mapper.admin.TeamMemberMapper;
import com.gt.hurecom.mapper.admin.UserMapper;
import com.gt.hurecom.repository.admin.TeamMemberRepository;
import com.gt.hurecom.service.admin.TeamMemberService;
import com.gt.hurecom.service.common.ReferenceDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private static final Logger log = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    private final TeamMemberRepository teamMemberRepository;

    private final TeamMemberMapper teamMemberMapper;

    private final UserMapper userMapper;

    private final TeamMapper teamMapper;

    private final ReferenceDataService referenceDataService;

    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository,
                                 TeamMemberMapper teamMemberMapper,
                                 UserMapper userMapper,
                                 TeamMapper teamMapper,
                                 ReferenceDataService referenceDataService) {
        super();
        this.teamMemberRepository = teamMemberRepository;
        this.teamMemberMapper = teamMemberMapper;
        this.userMapper = userMapper;
        this.teamMapper = teamMapper;
        this.referenceDataService = referenceDataService;
    }

    @Override
    public void createTeamMembers(TeamMemberRequest request) {
        log.debug("Service :: createTeamMembers :: Entered");

        Team team = referenceDataService.getTeamById(request.getTeamId());
        List<User> users = referenceDataService.getUsers(request.getUserIds());

        List<TeamMember> teamMembers = new ArrayList<>();
        LocalDate today = LocalDate.now();

        users.forEach(user -> {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeam(team);
            teamMember.setUser(user);
            teamMember.setStartDate(today);
            teamMembers.add(teamMember);
        });

        teamMemberRepository.saveAll(teamMembers);
        log.debug("Service :: createTeamMembers :: Exited");
    }

    @Override
    public void removeTeamMember(Long id) {
        log.debug("Service :: removeTeamMember :: Entered");

        TeamMember teamMember = teamMemberRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Team member not found."));

        teamMember.setEndDate(LocalDate.now());
        teamMemberRepository.save(teamMember);

        log.debug("Service :: removeTeamMember :: Exited");
    }

    @Override
    public List<TeamMemberResponse> getTeamMembers(Long teamId) {
        log.debug("Service :: getTeamMembers :: Entered");

        List<TeamMemberResponse> teamMembers = teamMemberRepository.findByTeam_IdAndEndDateIsNull(teamId).stream()
                .map(teamMemberMapper::convertToResponse).toList();

        log.debug("Service :: getTeamMembers :: Exited");
        return teamMembers;
    }

    @Override
    public List<UserResponse> getActiveUsersByTeam(Long teamId) {
        log.debug("Service :: getActiveUsersByTeam :: Entered");

        List<UserResponse> users = teamMemberRepository.findByTeam_IdAndEndDateIsNull(teamId).stream()
                .map(TeamMember::getUser).map(userMapper::convertToResponse).toList();

        log.debug("Service :: getActiveUsersByTeam :: Exited");
        return users;
    }

    @Override
    public List<TeamResponse> getActiveTeamsByUser(Long userId) {
        log.debug("Service :: getActiveTeamsByUser :: Entered");

        List<TeamResponse> teams = teamMemberRepository.findByUser_IdAndEndDateIsNull(userId).stream()
                .map(TeamMember::getTeam).map(teamMapper::convertToResponse).toList();

        log.debug("Service :: getActiveTeamsByUser :: Exited");
        return teams;
    }

    @Override
    public boolean isUserPartOfTeam(Long userId, Long teamId) {
        log.debug("Service :: isUserPartOfTeam :: Entered");

        boolean status = teamMemberRepository.existsByUser_IdAndTeam_IdAndEndDateIsNull(userId, teamId);

        log.debug("Service :: isUserPartOfTeam :: Exited");
        return status;
    }
}
