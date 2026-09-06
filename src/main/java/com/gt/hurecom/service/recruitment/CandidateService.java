package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.CandidateRequest;
import com.gt.hurecom.dto.recruitment.CandidateResponse;
import com.gt.hurecom.dto.recruitment.CandidateSearchRequest;
import com.gt.hurecom.exception.HurecomException;

public interface CandidateService {

    CandidateResponse createCandidate(CandidateRequest request);

    CandidateResponse updateCandidate(Long id, CandidateRequest request) throws HurecomException;

    CandidateResponse getCandidate(Long id);

    PageResponse<CandidateResponse> searchCandidates(CandidateSearchRequest request);

}
