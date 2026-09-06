package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.ApplicationRequest;
import com.gt.hurecom.dto.recruitment.ApplicationResponse;
import com.gt.hurecom.dto.recruitment.ApplicationSearchRequest;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.entity.recruitment.Application;
import com.gt.hurecom.entity.recruitment.Candidate;
import com.gt.hurecom.entity.recruitment.Job;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.recruitment.ApplicationMapper;
import com.gt.hurecom.repository.recruitment.ApplicationRepository;
import com.gt.hurecom.service.admin.TeamMemberService;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.ApplicationService;
import com.gt.hurecom.specification.recruitment.ApplicationSpecification;
import com.gt.hurecom.utility.CommonUtils;
import com.gt.hurecom.utility.PageUtils;
import com.gt.hurecom.utility.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    private final TeamMemberService teamMemberService;

    private final ReferenceDataService referenceDataService;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicationMapper applicationMapper,
                                  TeamMemberService teamMemberService,
                                  ReferenceDataService referenceDataService) {
        super();
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.teamMemberService = teamMemberService;
        this.referenceDataService = referenceDataService;
    }

    @Override
    public ApplicationResponse createApplication(ApplicationRequest request) {
        log.debug("Service :: createApplication :: Entered");

        if (applicationRepository.existsByJob_IdAndCandidate_Id(request.getJobId(), request.getCandidateId())) {
            CommonUtils.throwBusinessException("Candidate is already applied to the job.");
        }

        User user = SecurityUtils.getCurrentUser();

        if (!teamMemberService.isUserPartOfTeam(user.getId(), request.getTeamId())) {
            CommonUtils.throwBusinessException("User is not part of the team.");
        }

        Application application = applicationMapper.convertToEntity(request);
        Team team = referenceDataService.getTeamById(request.getTeamId());
        Job job = referenceDataService.getJobById(request.getJobId());
        Candidate candidate = referenceDataService.getCandidateById(request.getCandidateId());

        application.setOrganization(user.getOrganization());
        application.setRecruiter(user);
        application.setTeam(team);
        application.setJob(job);
        application.setCandidate(candidate);

        Application savedApplication = applicationRepository.save(application);

        log.debug("Service :: createApplication :: Exited");
        return applicationMapper.convertToResponse(savedApplication);
    }

    @Override
    public ApplicationResponse updateApplication(Long id, ApplicationRequest request) throws HurecomException {
        log.debug("Service :: updateApplication :: Entered");

        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Application not found."));

        if (applicationRepository.existsByJob_IdAndCandidate_IdAndIdNot(request.getJobId(), request.getCandidateId(), id)) {
            CommonUtils.throwBusinessException("Candidate is already applied to the job.");
        }

        User user = SecurityUtils.getCurrentUser();

        if (!existingApplication.getOrganization().getId().equals(user.getOrganization().getId())) {
            throw new HurecomException("Unauthorized access.");
        }

        applicationMapper.updateFromRequest(request, existingApplication);
        Application savedApplication = applicationRepository.save(existingApplication);

        log.debug("Service :: updateApplication :: Exited");
        return applicationMapper.convertToResponse(savedApplication);
    }

    @Override
    public ApplicationResponse getApplication(Long id) {
        log.debug("Service :: getApplication :: Entered");

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Application not found."));

        log.debug("Service :: getApplication :: Exited");
        return applicationMapper.convertToResponse(application);
    }

    @Override
    public PageResponse<ApplicationResponse> searchApplications(ApplicationSearchRequest request) {
        log.debug("Service :: searchApplications :: Entered");

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());

        User user = SecurityUtils.getCurrentUser();

        Specification<Application> spec = Specification
                .where(ApplicationSpecification.distinct())
                .and(ApplicationSpecification.organizationEquals(user.getOrganization().getId()))
                .and(ApplicationSpecification.jobCodeContains(request.getJobCode()))
                .and(ApplicationSpecification.candidateNameContains(request.getCandidateName()))
                .and(ApplicationSpecification.emailContains(request.getEmail()))
                .and(ApplicationSpecification.mobileNumberContains(request.getMobileNumber()))
                .and(ApplicationSpecification.skillsContains(request.getSkills()))
                .and(ApplicationSpecification.statusEquals(request.getStatusId()))
                .and(ApplicationSpecification.stageEquals(request.getStageId()));

        Page<Application> applicationPage = applicationRepository.findAll(spec, pageable);

        log.debug("Service :: searchApplications :: Exited");
        return PageUtils.convertToPageResponse(applicationPage, applicationMapper::convertToResponse);
    }
}
