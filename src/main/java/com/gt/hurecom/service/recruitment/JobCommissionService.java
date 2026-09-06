package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.recruitment.JobCommissionRequest;
import com.gt.hurecom.dto.recruitment.JobCommissionResponse;

import java.util.List;

public interface JobCommissionService {

    void createCommission(JobCommissionRequest request);

    List<JobCommissionResponse> getCommissions(Long jobId);

}
