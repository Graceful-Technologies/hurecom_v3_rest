package com.gt.hurecom.controller.admin;

import com.gt.hurecom.dto.admin.TeamListResponse;
import com.gt.hurecom.dto.admin.TeamRequest;
import com.gt.hurecom.dto.admin.TeamResponse;
import com.gt.hurecom.dto.common.ApiResponse;
import com.gt.hurecom.service.admin.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/teams")
public class TeamController {

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(@RequestBody TeamRequest request) {
        log.debug("Controller :: createTeam :: Entered");

        TeamResponse response = teamService.createTeam(request);

        log.debug("Controller :: createTeam :: Exited");
        return ResponseEntity.ok(ApiResponse.success("Team created successfully.", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamResponse>> updateTeam(@PathVariable Long id, @RequestBody TeamRequest request) {
        log.debug("Controller :: updateTeam :: Entered");

        TeamResponse response = teamService.updateTeam(id, request);

        log.debug("Controller :: updateTeam :: Exited");
        return ResponseEntity.ok(ApiResponse.success("Team updated successfully.", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamResponse>> getTeam(@PathVariable Long id) {
        log.debug("Controller :: getTeam :: Entered");

        TeamResponse response = teamService.getTeam(id);

        log.debug("Controller :: getTeam :: Exited");
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamListResponse>>> getTeams() {
        log.debug("Controller :: getTeams :: Entered");

        List<TeamListResponse> response = teamService.getTeams();

        log.debug("Controller :: getTeams :: Exited");
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("unassigned")
    public ResponseEntity<List<TeamResponse>> getTeamsNotAssignedToJob(
            @RequestParam("jobId") Long jobId
    ) {
        log.debug("Controller :: getTeamsNotAssignedToJob :: Entered");

        List<TeamResponse> response = teamService.getTeamsNotAssignedToJob(jobId);

        log.debug("Controller :: getTeamsNotAssignedToJob :: Exited");
        return ResponseEntity.ok(response);
    }
}
