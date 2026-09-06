package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.recruitment.JobAssignmentRequest;
import com.gt.hurecom.dto.recruitment.JobAssignmentResponse;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.recruitment.Job;
import com.gt.hurecom.entity.recruitment.JobAssignment;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.repository.recruitment.JobAssignmentRepository;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.JobAssignmentService;
import com.gt.hurecom.utility.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class JobAssignmentServiceImpl implements JobAssignmentService {

    private static final Logger log = LoggerFactory.getLogger(JobAssignmentServiceImpl.class);

    private final JobAssignmentRepository jobAssignmentRepository;

    private final ReferenceDataService referenceDataService;

    public JobAssignmentServiceImpl(JobAssignmentRepository jobAssignmentRepository,
                                    ReferenceDataService referenceDataService) {
        super();
        this.jobAssignmentRepository = jobAssignmentRepository;
        this.referenceDataService = referenceDataService;
    }

    @Override
    public void createAssignment(JobAssignmentRequest request) throws HurecomException {
        log.debug("Service :: createAssignment :: Entered");

        Job job = referenceDataService.getJobById(request.getJobId());
        List<Team> teams = referenceDataService.getTeams(request.getTeamIds());

        List<JobAssignment> assignments = new ArrayList<>();
        LocalDate today = LocalDate.now();

        teams.forEach(team -> {
            JobAssignment assignment = new JobAssignment();
            assignment.setJob(job);
            assignment.setTeam(team);
            assignment.setStartDate(today);
            assignments.add(assignment);
        });

        jobAssignmentRepository.saveAll(assignments);
        log.debug("Service :: createAssignment :: Exited");
    }

    @Override
    public void removeAssignment(Long id) {
        log.debug("Service :: removeAssignment :: Entered");

        JobAssignment assignment = jobAssignmentRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Assignment not found."));

        assignment.setEndDate(LocalDate.now());
        jobAssignmentRepository.save(assignment);

        log.debug("Service :: removeAssignment :: Exited");
    }

    @Override
    public List<JobAssignmentResponse> getAssignments(Long jobId) {
        log.debug("Service :: getAssignments :: Entered");

        if (Objects.isNull(jobId)) {
            CommonUtils.throwBusinessException("Job ID is required.");
        }

        List<JobAssignmentResponse> assignments = jobAssignmentRepository.findByJob_IdAndEndDateIsNull(jobId).stream()
                .map(this::convertToResponse).toList();

        log.debug("Service :: getAssignments :: Exited");
        return assignments;
    }

    private JobAssignmentResponse convertToResponse(JobAssignment jobAssignment) {
        JobAssignmentResponse response = new JobAssignmentResponse();
        response.setId(jobAssignment.getId());
        response.setTeamName(jobAssignment.getTeam().getName());
        response.setStartDate(jobAssignment.getStartDate());
        return response;
    }

}
