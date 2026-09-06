package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.recruitment.JobCommissionRequest;
import com.gt.hurecom.dto.recruitment.JobCommissionResponse;
import com.gt.hurecom.entity.recruitment.Job;
import com.gt.hurecom.entity.recruitment.JobCommission;
import com.gt.hurecom.repository.recruitment.JobCommissionRepository;
import com.gt.hurecom.service.common.CacheService;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.JobCommissionService;
import com.gt.hurecom.utility.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class JobCommissionServiceImpl implements JobCommissionService {

    private static final Logger log = LoggerFactory.getLogger(JobCommissionServiceImpl.class);

    private final JobCommissionRepository jobCommissionRepository;

    private final ReferenceDataService referenceDataService;

    private final CacheService cacheService;

    public JobCommissionServiceImpl(JobCommissionRepository jobCommissionRepository,
                                    ReferenceDataService referenceDataService,
                                    CacheService cacheService) {
        super();
        this.jobCommissionRepository = jobCommissionRepository;
        this.referenceDataService = referenceDataService;
        this.cacheService = cacheService;
    }

    @Override
    public void createCommission(JobCommissionRequest request) {
        log.debug("Service :: createCommission :: Entered");

        // End Date existing commission.
        Optional<JobCommission> existingCommission = jobCommissionRepository
                .findByJob_IdAndEndDateIsNull(request.getJobId());

        existingCommission.ifPresent(commission -> {
            commission.setEndDate(LocalDate.now());
            jobCommissionRepository.save(commission);
        });

        Job job = referenceDataService.getJobById(request.getJobId());

        // creat new commission
        JobCommission newCommission = new JobCommission();
        newCommission.setCommissionType(request.getCommissionType());
        newCommission.setCommissionValue(request.getCommissionValue());
        newCommission.setGuaranteePeriodDays(request.getGuaranteePeriodDays());
        newCommission.setJob(job);
        newCommission.setStartDate(LocalDate.now());
        jobCommissionRepository.save(newCommission);

        log.debug("Service :: createCommission :: Exited");
    }

    @Override
    public List<JobCommissionResponse> getCommissions(Long jobId) {
        log.debug("Service :: getCommissions :: Entered");

        if (Objects.isNull(jobId)) {
            CommonUtils.throwBusinessException("Job ID is required.");
        }

        List<JobCommissionResponse> commissions = jobCommissionRepository.findByJob_Id(jobId).stream()
                .map(commission -> {
                    JobCommissionResponse jobCommissionResponse = new JobCommissionResponse();
                    jobCommissionResponse.setId(commission.getId());
                    jobCommissionResponse.setCommissionType(commission.getCommissionType());
                    jobCommissionResponse.setCommissionValue(commission.getCommissionValue());
                    jobCommissionResponse.setCommissionTypeValue(
                            cacheService.getName("COMMISSION_TYPE", jobCommissionResponse.getCommissionType()));
                    jobCommissionResponse.setGuaranteePeriodDays(commission.getGuaranteePeriodDays());
                    jobCommissionResponse.setStartDate(commission.getStartDate());
                    jobCommissionResponse.setEndDate(commission.getEndDate());
                    return jobCommissionResponse;
                }).toList();

        log.debug("Service :: getCommissions :: Exited");
        return commissions;
    }
}
