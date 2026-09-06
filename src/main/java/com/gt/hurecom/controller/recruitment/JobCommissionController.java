package com.gt.hurecom.controller.recruitment;

import com.gt.hurecom.dto.common.ApiResponse;
import com.gt.hurecom.dto.recruitment.JobCommissionRequest;
import com.gt.hurecom.dto.recruitment.JobCommissionResponse;
import com.gt.hurecom.service.recruitment.JobCommissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment/job-commissions")
public class JobCommissionController {

    private static final Logger log = LoggerFactory.getLogger(JobCommissionController.class);

    private final JobCommissionService jobCommissionService;

    public JobCommissionController(JobCommissionService jobCommissionService) {
        this.jobCommissionService = jobCommissionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createCommission(@RequestBody JobCommissionRequest request) {
        log.debug("Controller :: createCommission :: Entered");

        jobCommissionService.createCommission(request);

        log.debug("Controller :: createCommission :: Exited");
        return ResponseEntity.ok(ApiResponse.success("Commission details updated successfully"));
    }

    @GetMapping
    public ResponseEntity<List<JobCommissionResponse>> getCommissions(@RequestParam Long jobId) {
        log.debug("Controller :: getCommissions :: Entered");

        List<JobCommissionResponse> response = jobCommissionService.getCommissions(jobId);

        log.debug("Controller :: getCommissions :: Exited");
        return ResponseEntity.ok(response);
    }
}
