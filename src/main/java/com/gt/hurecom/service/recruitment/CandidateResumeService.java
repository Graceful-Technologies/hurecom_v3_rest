package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.common.FileDownloadResponse;
import com.gt.hurecom.dto.recruitment.CandidateResumeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateResumeService {

    CandidateResumeResponse uploadResume(Long candidateId, MultipartFile file);

    FileDownloadResponse downloadResume(Long id);

    List<CandidateResumeResponse> getResumes(Long jobId);

}
