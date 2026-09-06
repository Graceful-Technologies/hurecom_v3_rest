package com.gt.hurecom.controller.recruitment;

import com.gt.hurecom.dto.common.ApiResponse;
import com.gt.hurecom.dto.common.FileDownloadResponse;
import com.gt.hurecom.dto.recruitment.JobAttachmentRequest;
import com.gt.hurecom.dto.recruitment.JobAttachmentResponse;
import com.gt.hurecom.service.recruitment.JobAttachmentService;
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
@RequestMapping("/api/recruitment/job-attachments")
public class JobAttachmentController {

    private static final Logger log = LoggerFactory.getLogger(JobAttachmentController.class);

    private final JobAttachmentService jobAttachmentService;

    public JobAttachmentController(JobAttachmentService jobAttachmentService) {
        this.jobAttachmentService = jobAttachmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> uploadAttachment(@Valid @ModelAttribute JobAttachmentRequest request) {
        log.debug("Controller :: uploadAttachment :: Entered");

        jobAttachmentService.uploadAttachment(request);

        log.debug("Controller :: uploadAttachment :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Attachments uploaded successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeAttachment(@PathVariable Long id) {
        log.debug("Controller :: removeAttachment :: Entered");

        jobAttachmentService.removeAttachment(id);

        log.debug("Controller :: removeAttachment :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Attachment removed successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id) {
        log.debug("Controller :: downloadAttachment :: Entered");

        FileDownloadResponse response = jobAttachmentService.downloadAttachment(id);

        log.debug("Controller :: downloadAttachment :: Exited");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + response.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response.getResource());
    }

    @GetMapping
    public ResponseEntity<List<JobAttachmentResponse>> getAttachments(@RequestParam Long jobId) {
        log.debug("Controller :: getAttachments :: Entered");

        List<JobAttachmentResponse> response = jobAttachmentService.getAttachments(jobId);

        log.debug("Controller :: getAttachments :: Exited");
        return ResponseEntity.ok(response);
    }

}
