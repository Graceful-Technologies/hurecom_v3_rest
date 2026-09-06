package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.JobRequest;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.dto.recruitment.JobSearchRequest;
import com.gt.hurecom.exception.HurecomException;
import org.springframework.data.domain.Page;

public interface JobService {

    JobResponse createJob(JobRequest request) throws HurecomException;

    JobResponse updateJob(Long id, JobRequest request) throws HurecomException;

    JobResponse getJob(Long id);

    PageResponse<JobResponse> searchJobs(JobSearchRequest request);

    PageResponse<JobResponse> searchJobOpenings(JobSearchRequest request);

}
