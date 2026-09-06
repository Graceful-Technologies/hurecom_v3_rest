package com.gt.hurecom.controller.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.CandidateRequest;
import com.gt.hurecom.dto.recruitment.CandidateResponse;
import com.gt.hurecom.dto.recruitment.CandidateSearchRequest;
import com.gt.hurecom.service.recruitment.CandidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment/candidates")
public class CandidateController {

    private static final Logger log = LoggerFactory.getLogger(CandidateController.class);

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(@ModelAttribute CandidateRequest request) {
        log.debug("Controller :: createCandidate :: Entered");

        CandidateResponse response = candidateService.createCandidate(request);

        log.debug("Controller :: createCandidate :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable Long id, @ModelAttribute CandidateRequest request) {
        log.debug("Controller :: updateCandidate :: Entered");

        CandidateResponse response = candidateService.updateCandidate(id, request);

        log.debug("Controller :: updateCandidate :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidate(@PathVariable Long id) {
        log.debug("Controller :: getCandidate :: Entered");

        CandidateResponse response = candidateService.getCandidate(id);

        log.debug("Controller :: getCandidate :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<CandidateResponse>> searchCandidates(@RequestBody CandidateSearchRequest request) {
        log.debug("Controller :: searchCandidates :: Entered");

        PageResponse<CandidateResponse> response = candidateService.searchCandidates(request);

        log.debug("Controller :: searchCandidates :: Exited");
        return ResponseEntity.ok(response);
    }

}
