package com.gt.hurecom.dto.recruitment;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class JobAttachmentRequest {

    @NotNull(message = "Job ID is required.")
    private Long jobId;

    @NotNull(message = "At least one attachment is required.")
    private List<MultipartFile> files;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
