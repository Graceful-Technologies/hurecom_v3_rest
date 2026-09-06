package com.gt.hurecom.controller.admin;

import com.gt.hurecom.dto.admin.TeamMemberRequest;
import com.gt.hurecom.dto.admin.TeamMemberResponse;
import com.gt.hurecom.dto.common.ApiResponse;
import com.gt.hurecom.service.admin.TeamMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/team-members")
public class TeamMemberController {

    private static final Logger log = LoggerFactory.getLogger(TeamMemberController.class);

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createTeamMembers(@RequestBody TeamMemberRequest request) {
        log.debug("Controller :: createTeamMembers :: Entered");

        teamMemberService.createTeamMembers(request);

        log.debug("Controller :: createTeamMembers :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Team members added successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeTeamMember(@PathVariable Long id) {
        log.debug("Controller :: removeTeamMember :: Entered");

        teamMemberService.removeTeamMember(id);

        log.debug("Controller :: removeTeamMember :: Exited");
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Team member removed successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamMemberResponse>>> getTeamMembers(@RequestParam("teamId") Long teamId) {
        log.debug("Controller :: getTeamMembers :: Entered");

        List<TeamMemberResponse> response = teamMemberService.getTeamMembers(teamId);

        log.debug("Controller :: getTeamMembers :: Exited");
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
