package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.JobRequest;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.dto.recruitment.JobSearchRequest;
import com.gt.hurecom.entity.master.Client;
import com.gt.hurecom.entity.master.ClientLocation;
import com.gt.hurecom.entity.master.ClientSpoc;
import com.gt.hurecom.entity.recruitment.Job;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.recruitment.JobMapper;
import com.gt.hurecom.repository.recruitment.JobRepository;
import com.gt.hurecom.service.common.CacheService;
import com.gt.hurecom.service.common.CodeGeneratorService;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.JobService;
import com.gt.hurecom.utility.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    private final ReferenceDataService referenceDataService;

    private final CodeGeneratorService codeGeneratorService;

    private final CacheService cacheService;

    public JobServiceImpl(JobRepository jobRepository,
                          JobMapper jobMapper,
                          ReferenceDataService referenceDataService,
                          CodeGeneratorService codeGeneratorService,
                          CacheService cacheService) {
        super();
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.referenceDataService = referenceDataService;
        this.codeGeneratorService = codeGeneratorService;
        this.cacheService = cacheService;
    }

    @Override
    public JobResponse createJob(JobRequest request) throws HurecomException {
        log.debug("Service :: createJob :: Entered");

        if (jobRepository.existsByClient_IdAndClientLocation_IdAndTitleIgnoreCase(request.getClientId(),
                request.getClientLocationId(), request.getTitle())) {
            CommonUtils.throwBusinessException("Client, Location and Title combination already exists.");
        }

        Job job = jobMapper.convertToEntity(request);
        job.setJobCode(codeGeneratorService.generateCode("JOB", "JOB"));

        Client client = referenceDataService.getClientById(request.getClientId());
        job.setClient(client);

        ClientLocation clientLocation = referenceDataService.getClientLocationById(request.getClientLocationId());
        job.setClientLocation(clientLocation);

        ClientSpoc clientSpoc = referenceDataService.getClientSpocById(request.getClientSpocId());
        job.setClientSpoc(clientSpoc);

        Job savedJob = jobRepository.save(job);

        log.debug("Service :: createJob :: Exited");
        return jobMapper.convertToResponse(savedJob);
    }

    @Override
    public JobResponse updateJob(Long id, JobRequest request) throws HurecomException {
        log.debug("Service :: updateJob :: Entered");

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Job not found."));


        if (jobRepository.existsByClient_IdAndClientLocation_IdAndTitleIgnoreCaseAndIdNot(request.getClientId(),
                request.getClientLocationId(), request.getTitle(), id)) {
            CommonUtils.throwBusinessException("Client, Location and Title combination already exists.");
        }

        if (!existingJob.getClientSpoc().getId().equals(request.getClientSpocId())) {
            ClientSpoc clientSpoc = referenceDataService.getClientSpocById(request.getClientSpocId());
            existingJob.setClientSpoc(clientSpoc);
        }

        jobMapper.updateFromRequest(request, existingJob);
        Job savedJob = jobRepository.save(existingJob);

        log.debug("Service :: updateJob :: Exited");
        return jobMapper.convertToResponse(savedJob);
    }

    @Override
    public JobResponse getJob(Long id) {
        log.debug("Service :: getJob :: Entered");

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Job not found."));

        log.debug("Service :: getJob :: Exited");
        return convertToResponse(job);
    }

    @Override
    public PageResponse<JobResponse> searchJobs(JobSearchRequest request) {
        log.debug("Service :: searchJobs :: Entered");

        PageResponse<JobResponse> response = jobRepository.searchJobs(request);

        response.getContent().forEach(jobResponse -> {
            jobResponse.setStatusValue(cacheService.getName("JOB_STATUS", jobResponse.getStatus()));
            jobResponse.setWorkModeValue(cacheService.getName("WORK_MODE", jobResponse.getWorkMode()));
            jobResponse.setEmploymentTypeValue(cacheService.getName("EMPLOYMENT_TYPE", jobResponse.getEmploymentType()));
        });

        log.debug("Service :: searchJobs :: Exited");
        return response;
    }

    @Override
    public PageResponse<JobResponse> searchJobOpenings(JobSearchRequest request) {
        log.debug("Service :: searchJobOpenings :: Entered");

        request.setStatus("OPEN");
        PageResponse<JobResponse> response = searchJobs(request);

        log.debug("Service :: searchJobOpenings :: Exited");
        return response;
    }

    private JobResponse convertToResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setJobCode(job.getJobCode());
        response.setOpenPositions(job.getOpenPositions());
        response.setMinExperience(job.getMinExperience());
        response.setMaxExperience(job.getMaxExperience());
        response.setMaxCtc(job.getMaxCtc());
        response.setClientId(job.getClient().getId());
        response.setClientName(job.getClient().getName());
        response.setClientLocationId(job.getClientLocation().getId());
        response.setClientLocation(mapClientLocation(job.getClientLocation()));
        response.setClientSpocId(job.getClientSpoc().getId());
        response.setClientSpocName(job.getClientSpoc().getName());
        response.setSkills(mapSkillsToList(job.getSkills()));

        response.setStatus(job.getStatus());
        response.setStatusValue(cacheService.getName("JOB_STATUS", job.getStatus()));

        response.setWorkMode(job.getWorkMode());
        response.setWorkModeValue(cacheService.getName("WORK_MODE", job.getWorkMode()));

        response.setEmploymentType(job.getEmploymentType());
        response.setEmploymentTypeValue(cacheService.getName("EMPLOYMENT_TYPE", job.getEmploymentType()));

        response.setJobDescription(job.getJobDescription());
        response.setCreatedDate(job.getCreatedDate());
        response.setCreatedUserName(job.getCreatedUser().getName());
        return response;
    }

    private String mapClientLocation(ClientLocation clientLocation) {
        if (clientLocation == null) {
            return null;
        }
        return clientLocation.getBranchName() + ", " + clientLocation.getCity().getName();
    }

    private List<String> mapTeams(Job job) {
        if (job.getAssignments() == null) {
            return List.of();
        }
        return job.getAssignments().stream()
                .map(a -> a.getTeam().getName())
                .distinct()
                .toList();
    }

    private String mapSkillsToString(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return null;
        }
        return String.join(",", skills);
    }

    private List<String> mapSkillsToList(String skills) {
        if (skills == null || skills.isBlank()) {
            return List.of();
        }
        return List.of(skills.split(","));
    }
}