package com.gt.hurecom.controller.recruitment;

import com.gt.hurecom.dto.common.FileDownloadResponse;
import com.gt.hurecom.dto.recruitment.CandidateResumeRequest;
import com.gt.hurecom.dto.recruitment.CandidateResumeResponse;
import com.gt.hurecom.service.recruitment.CandidateResumeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment/candidate-resumes")
public class CandidateResumeController {

    private static final Logger log = LoggerFactory.getLogger(CandidateResumeController.class);

    private final CandidateResumeService candidateResumeService;

    public CandidateResumeController(CandidateResumeService candidateResumeService) {
        this.candidateResumeService = candidateResumeService;
    }

    @PostMapping
    public ResponseEntity<CandidateResumeResponse> uploadResume(@Valid @ModelAttribute CandidateResumeRequest request) {
        log.debug("Controller :: uploadAttachment :: Entered");

        CandidateResumeResponse response = candidateResumeService.uploadResume(request.getClientId(), request.getFile());

        log.debug("Controller :: uploadAttachment :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
        log.debug("Controller :: downloadResume :: Entered");

        FileDownloadResponse response = candidateResumeService.downloadResume(id);

        log.debug("Controller :: downloadResume :: Exited");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + response.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response.getResource());
    }

    @GetMapping
    public ResponseEntity<List<CandidateResumeResponse>> getResumes(@RequestParam Long clientId) {
        log.debug("Controller :: getResumes :: Entered");

        List<CandidateResumeResponse> response = candidateResumeService.getResumes(clientId);

        log.debug("Controller :: getResumes :: Exited");
        return ResponseEntity.ok(response);
    }
}
