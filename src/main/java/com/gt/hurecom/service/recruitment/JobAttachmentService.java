package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.common.FileDownloadResponse;
import com.gt.hurecom.dto.recruitment.JobAttachmentRequest;
import com.gt.hurecom.dto.recruitment.JobAttachmentResponse;

import java.util.List;

public interface JobAttachmentService {

    void uploadAttachment(JobAttachmentRequest request);

    void removeAttachment(Long id);

    FileDownloadResponse downloadAttachment(Long id);

    List<JobAttachmentResponse> getAttachments(Long jobId);

}
