package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.dto.recruitment.JobSearchRequest;

public interface JobRepositoryCustom {

    PageResponse<JobResponse> searchJobs(JobSearchRequest request);

}
