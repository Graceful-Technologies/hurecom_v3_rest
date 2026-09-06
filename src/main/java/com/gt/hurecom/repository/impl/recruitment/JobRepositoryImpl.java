package com.gt.hurecom.repository.impl.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.JobListResponse;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.dto.recruitment.JobSearchRequest;
import com.gt.hurecom.repository.recruitment.JobRepositoryCustom;
import com.gt.hurecom.utility.QueryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@Repository
public class JobRepositoryImpl implements JobRepositoryCustom {

    private final EntityManager entityManager;

    public JobRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final BiFunction<JobSearchRequest, List<Object>, String> SEARCH_JOBS_PARAMETERS = (
            request, params) -> {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    };

    private static final BiFunction<JobSearchRequest, List<Object>, String> SEARCH_JOBS = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                SELECT new com.gt.hurecom.dto.recruitment.JobListResponse(
                j.id,
                j.client.id,
                j.client.name,
                j.clientLocation.id,
                j.clientLocation.branchName,
                j.clientSpoc.id,
                j.clientSpoc.name,
                j.jobCode,
                j.title,
                j.openPositions,
                j.jobDescription,
                j.minExperience,
                j.maxExperience,
                j.maxCtc,
                j.skills,
                j.employmentType,
                j.workMode,
                j.status
                )
                FROM Job j
                LEFT JOIN j.client c
                LEFT JOIN j.clientLocation cl
                LEFT JOIN j.clientSpoc cs
                WHERE 1 = 1
                """);

        String parameters = SEARCH_JOBS_PARAMETERS.apply(request, params);

        if (StringUtils.hasText(parameters)) {
            sb.append(parameters);
        }

        return sb.toString();
    };

    private static final BiFunction<JobSearchRequest, List<Object>, String> GET_JOBS_COUNT = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                SELECT COUNT(DISTINCT j.id)
                FROM Job j
                WHERE 1 = 1
                """);

        String parameters = SEARCH_JOBS_PARAMETERS.apply(request, params);

        if (StringUtils.hasText(parameters)) {
            sb.append(parameters);
        }

        return sb.toString();
    };

    @Override
    public PageResponse<JobResponse> searchJobs(JobSearchRequest request) {
        List<Object> params = new ArrayList<>();
        String jpql = SEARCH_JOBS.apply(request, params);

        TypedQuery<JobListResponse> query = entityManager.createQuery(jpql, JobListResponse.class);

        QueryUtils.setParameters(query, params);
        QueryUtils.setPagination(query, request.getPage(), request.getLimit());

        List<JobListResponse> content = query.getResultList();
        Long totalElements = getJobsCount(request);

        List<JobResponse> jobs = new ArrayList<>();

        for (JobListResponse jobListResponse : content) {
            JobResponse jobResponse = new JobResponse();
            jobResponse.setId(jobListResponse.id());
            jobResponse.setClientId(jobListResponse.clientId());
            jobResponse.setClientName(jobListResponse.clientName());
            jobResponse.setClientLocationId(jobListResponse.clientLocationId());
            jobResponse.setClientLocation(jobListResponse.clientLocation());
            jobResponse.setClientSpocId(jobListResponse.clientSpocId());
            jobResponse.setClientSpocName(jobListResponse.clientSpocName());
            jobResponse.setJobCode(jobListResponse.jobCode());
            jobResponse.setTitle(jobListResponse.title());
            jobResponse.setOpenPositions(jobListResponse.openPositions());
            jobResponse.setEmploymentType(jobListResponse.employmentType());
            jobResponse.setWorkMode(jobListResponse.workMode());
            jobResponse.setMaxCtc(jobListResponse.maxCtc());
            jobResponse.setMinExperience(jobListResponse.minExperience());
            jobResponse.setMaxExperience(jobListResponse.maxExperience());
            jobResponse.setStatus(jobListResponse.status());
            jobResponse.setJobDescription(jobListResponse.jobDescription());

            List<String> skills = Arrays.stream(jobListResponse.skills().split(",")).map(String::trim).toList();
            jobResponse.setSkills(skills);

            jobs.add(jobResponse);
        }

        return new PageResponse<>(jobs, totalElements);
    }

    private Long getJobsCount(JobSearchRequest request) {

        List<Object> params = new ArrayList<>();
        String jpql = GET_JOBS_COUNT.apply(request, params);

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        QueryUtils.setParameters(query, params);

        return query.getSingleResult();
    }
}
