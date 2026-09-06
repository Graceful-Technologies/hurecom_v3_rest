package com.gt.hurecom.controller.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.JobRequest;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.dto.recruitment.JobSearchRequest;
import com.gt.hurecom.service.recruitment.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment/jobs")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest request) {
        log.debug("Controller :: createJob :: Entered");

        JobResponse response = jobService.createJob(request);

        log.debug("Controller :: createJob :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @RequestBody JobRequest request) {
        log.debug("Controller :: updateJob :: Entered");

        JobResponse response = jobService.updateJob(id, request);

        log.debug("Controller :: updateJob :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable Long id) {
        log.debug("Controller :: getJob :: Entered");

        JobResponse response = jobService.getJob(id);

        log.debug("Controller :: getJob :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<JobResponse>> searchJobs(@RequestBody JobSearchRequest request) {

        log.debug("Controller :: searchJobs :: Entered");

        PageResponse<JobResponse> response = jobService.searchJobs(request);

        log.debug("Controller :: searchJobs :: Exited");
        return ResponseEntity.ok(response);
    }
}
