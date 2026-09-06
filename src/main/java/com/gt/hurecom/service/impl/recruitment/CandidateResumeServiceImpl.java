package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.common.FileDownloadResponse;
import com.gt.hurecom.dto.common.FileUploadResponse;
import com.gt.hurecom.dto.recruitment.CandidateResumeResponse;
import com.gt.hurecom.entity.recruitment.Candidate;
import com.gt.hurecom.entity.recruitment.CandidateResume;
import com.gt.hurecom.enums.FileCategory;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.recruitment.CandidateResumeMapper;
import com.gt.hurecom.repository.recruitment.CandidateResumeRepository;
import com.gt.hurecom.service.common.FileService;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.CandidateResumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CandidateResumeServiceImpl implements CandidateResumeService {

    private static final Logger log = LoggerFactory.getLogger(CandidateResumeServiceImpl.class);

    private final CandidateResumeRepository candidateResumeRepository;

    private final CandidateResumeMapper candidateResumeMapper;

    private final ReferenceDataService referenceDataService;

    private final FileService fileService;

    public CandidateResumeServiceImpl(CandidateResumeRepository candidateResumeRepository,
                                      CandidateResumeMapper candidateResumeMapper,
                                      ReferenceDataService referenceDataService,
                                      FileService fileService) {
        super();
        this.candidateResumeRepository = candidateResumeRepository;
        this.candidateResumeMapper = candidateResumeMapper;
        this.referenceDataService = referenceDataService;
        this.fileService = fileService;
    }

    @Override
    public CandidateResumeResponse uploadResume(Long candidateId, MultipartFile file) {
        log.debug("Service :: uploadResume :: Entered");

        Candidate candidate = referenceDataService.getCandidateById(candidateId);

        FileUploadResponse uploadResponse = fileService.upload(file, FileCategory.RESUME, candidateId);

        CandidateResume resume = new CandidateResume();
        resume.setFileName(uploadResponse.getFileName());
        resume.setFileType(uploadResponse.getFileType());
        resume.setFilePath(uploadResponse.getFilePath());
        resume.setFileSize(uploadResponse.getFileSize());
        resume.setCandidate(candidate);

        CandidateResume savedResume = candidateResumeRepository.save(resume);

        log.debug("Service :: uploadResume :: Exited");
        return candidateResumeMapper.convertToResponse(savedResume);
    }

    @Override
    public FileDownloadResponse downloadResume(Long id) {
        log.debug("Service :: downloadResume :: Entered");

        CandidateResume resume = candidateResumeRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Resume not found."));

        Resource resource = fileService.download(resume.getFilePath());

        FileDownloadResponse response = new FileDownloadResponse();
        response.setResource(resource);
        response.setFileName(resume.getFileName());
        response.setFileType(resume.getFileType());

        log.debug("Service :: downloadResume :: Exited");
        return response;
    }

    @Override
    public List<CandidateResumeResponse> getResumes(Long candidateId) {
        log.debug("Service :: getResumes :: Entered");

        List<CandidateResumeResponse> resumes = candidateResumeRepository.findByCandidate_Id(candidateId).stream()
                .map(candidateResumeMapper::convertToResponse).toList();

        log.debug("Service :: getResumes :: Exited");
        return resumes;
    }

}
