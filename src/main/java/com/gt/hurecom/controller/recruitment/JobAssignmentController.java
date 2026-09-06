package com.gt.hurecom.controller.recruitment;

import com.gt.hurecom.dto.common.ApiResponse;
import com.gt.hurecom.dto.recruitment.JobAssignmentRequest;
import com.gt.hurecom.dto.recruitment.JobAssignmentResponse;
import com.gt.hurecom.service.recruitment.JobAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment/job-assignments")
public class JobAssignmentController {

    private static final Logger log = LoggerFactory.getLogger(JobAssignmentController.class);

    private final JobAssignmentService jobAssignmentService;

    public JobAssignmentController(JobAssignmentService jobAssignmentService) {
        this.jobAssignmentService = jobAssignmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createAssignment(@RequestBody JobAssignmentRequest request) {
        log.debug("Controller :: createAssignment :: Entered");

        jobAssignmentService.createAssignment(request);

        log.debug("Controller :: createAssignment :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Teams assigned to the job successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeAssignment(@PathVariable Long id) {
        log.debug("Controller :: removeAssignment :: Entered");

        jobAssignmentService.removeAssignment(id);

        log.debug("Controller :: removeAssignment :: Exited");
        return ResponseEntity.ok()
                .body(ApiResponse.success("Team unassigned from the job successfully"));
    }

    @GetMapping
    public ResponseEntity<List<JobAssignmentResponse>> getAssignments(@RequestParam Long jobId) {
        log.debug("Controller :: getAssignments :: Entered");

        List<JobAssignmentResponse> response = jobAssignmentService.getAssignments(jobId);

        log.debug("Controller :: getAssignments :: Exited");
        return ResponseEntity.ok(response);
    }

}
