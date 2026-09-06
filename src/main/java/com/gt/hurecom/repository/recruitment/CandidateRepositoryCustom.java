package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.CandidateResponse;
import com.gt.hurecom.dto.recruitment.CandidateSearchRequest;

public interface CandidateRepositoryCustom {

    PageResponse<CandidateResponse> searchCandidates(CandidateSearchRequest request);
}
