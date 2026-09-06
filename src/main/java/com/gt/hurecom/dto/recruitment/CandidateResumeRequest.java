package com.gt.hurecom.dto.recruitment;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class CandidateResumeRequest {

    @NotNull(message = "Client ID is required.")
    private Long clientId;

    @NotNull(message = "Resume is required.")
    private MultipartFile file;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
