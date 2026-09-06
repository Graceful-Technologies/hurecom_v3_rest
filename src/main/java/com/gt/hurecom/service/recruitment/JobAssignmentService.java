package com.gt.hurecom.service.recruitment;

import com.gt.hurecom.dto.recruitment.JobAssignmentRequest;
import com.gt.hurecom.dto.recruitment.JobAssignmentResponse;
import com.gt.hurecom.exception.HurecomException;

import java.util.List;

public interface JobAssignmentService {

    void createAssignment(JobAssignmentRequest request) throws HurecomException;

    void removeAssignment(Long id);

    List<JobAssignmentResponse> getAssignments(Long jobId);
}
