package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.CandidateRequest;
import com.gt.hurecom.dto.recruitment.CandidateResponse;
import com.gt.hurecom.dto.recruitment.CandidateSearchRequest;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.recruitment.Candidate;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.recruitment.CandidateMapper;
import com.gt.hurecom.repository.recruitment.CandidateRepository;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.CandidateResumeService;
import com.gt.hurecom.service.recruitment.CandidateService;
import com.gt.hurecom.utility.CommonUtils;
import com.gt.hurecom.utility.PageUtils;
import com.gt.hurecom.utility.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private static final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    private final CandidateMapper candidateMapper;

    private final CandidateResumeService candidateResumeService;

    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                CandidateMapper candidateMapper,
                                CandidateResumeService candidateResumeService,
                                ReferenceDataService referenceDataService) {
        super();
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
        this.candidateResumeService = candidateResumeService;
    }

    @Override
    public CandidateResponse createCandidate(CandidateRequest request) {
        log.debug("Service :: createCandidate :: Entered");

        Organization organization = SecurityUtils.getCurrentOrganization();

        if (candidateRepository.existsByEmailIgnoreCaseAndOrganization_Id(request.getEmail(), organization.getId())) {
            CommonUtils.throwBusinessException("Email already exists.");
        }

        if (candidateRepository.existsByMobileNumberAndOrganization_Id(request.getMobileNumber(), organization.getId())) {
            CommonUtils.throwBusinessException("Mobile number already exists.");
        }

        Candidate candidate = candidateMapper.convertToEntity(request);
        candidate.setOrganization(organization);

        Candidate savedCandidate = candidateRepository.save(candidate);

        if (Objects.nonNull(request.getResume()) && !request.getResume().isEmpty()) {
            candidateResumeService.uploadResume(savedCandidate.getId(), request.getResume());
        }

        log.debug("Service :: createCandidate :: Exited");
        return candidateMapper.convertToResponse(savedCandidate);
    }

    @Override
    public CandidateResponse updateCandidate(Long id, CandidateRequest request) throws HurecomException {
        log.debug("Service :: updateCandidate :: Entered");

        Candidate existingCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Candidate not found."));

        if (!existingCandidate.getEmail().equalsIgnoreCase(request.getEmail()) &&
                candidateRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
            CommonUtils.throwBusinessException("Email already exists.");
        }

        if (!existingCandidate.getMobileNumber().equalsIgnoreCase(request.getMobileNumber())
                && candidateRepository.existsByMobileNumberAndIdNot(request.getMobileNumber(), id)) {
            CommonUtils.throwBusinessException("Mobile number already exists.");
        }

        candidateMapper.updateFromRequest(request, existingCandidate);
        Candidate savedCandidate = candidateRepository.save(existingCandidate);

        log.debug("Service :: updateCandidate :: Exited");
        return candidateMapper.convertToResponse(savedCandidate);
    }

    @Override
    public CandidateResponse getCandidate(Long id) {
        log.debug("Service :: getCandidate :: Entered");

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Candidate not found."));

        log.debug("Service :: getCandidate :: Exited");
        return candidateMapper.convertToResponse(candidate);
    }

    @Override
    public PageResponse<CandidateResponse> searchCandidates(CandidateSearchRequest request) {
        log.debug("Service :: searchCandidates :: Entered");

        PageResponse<CandidateResponse> response = candidateRepository.searchCandidates(request);

        log.debug("Service :: searchCandidates :: Exited");
        return response;
    }
}
