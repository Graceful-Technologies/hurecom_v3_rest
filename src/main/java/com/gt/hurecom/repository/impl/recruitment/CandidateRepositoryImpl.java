package com.gt.hurecom.repository.impl.recruitment;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.recruitment.CandidateListResponse;
import com.gt.hurecom.dto.recruitment.CandidateResponse;
import com.gt.hurecom.dto.recruitment.CandidateResumeResponse;
import com.gt.hurecom.dto.recruitment.CandidateSearchRequest;
import com.gt.hurecom.repository.recruitment.CandidateRepositoryCustom;
import com.gt.hurecom.utility.QueryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@Repository
public class CandidateRepositoryImpl implements CandidateRepositoryCustom {

    private final EntityManager entityManager;

    public CandidateRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final BiFunction<CandidateSearchRequest, List<Object>, String> SEARCH_CANDIDATES_PARAMETERS = (
            request, params) -> {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    };

    private static final BiFunction<CandidateSearchRequest, List<Object>, String> SEARCH_CANDIDATES = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                SELECT new com.gt.hurecom.dto.recruitment.CandidateListResponse(
                c.id,
                c.name,
                c.email,
                c.mobileNumber,
                c.alternateMobileNumber,
                c.gender,
                c.dateOfBirth,
                c.currentCompany,
                c.currentDesignation,
                c.totalExperience,
                c.relevantExperience,
                c.skills,
                c.qualification,
                c.currentCtc,
                c.expectedCtc,
                c.noticePeriod,
                c.lastWorkingDay,
                c.currentLocation,
                c.preferredLocation,
                c.workModePreference,
                c.source,
                c.remarks,
                
                r.id,
                r.fileName,
                r.filePath,
                r.fileType,
                r.fileSize
                )
                FROM Candidate c
                LEFT JOIN CandidateResume r ON r.candidate.id = c.id
                WHERE 1 = 1
                """);

        String parameters = SEARCH_CANDIDATES_PARAMETERS.apply(request, params);

        if (StringUtils.hasText(parameters)) {
            sb.append(parameters);
        }

        return sb.toString();
    };

    private static final BiFunction<CandidateSearchRequest, List<Object>, String> GET_CANDIDATES_COUNT = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                SELECT COUNT(DISTINCT c.id)
                FROM Candidate c
                WHERE 1 = 1
                """);

        String parameters = SEARCH_CANDIDATES_PARAMETERS.apply(request, params);

        if (StringUtils.hasText(parameters)) {
            sb.append(parameters);
        }

        return sb.toString();
    };


    @Override
    public PageResponse<CandidateResponse> searchCandidates(CandidateSearchRequest request) {
        List<Object> params = new ArrayList<>();
        String jpql = SEARCH_CANDIDATES.apply(request, params);

        TypedQuery<CandidateListResponse> query = entityManager.createQuery(jpql, CandidateListResponse.class);

        QueryUtils.setParameters(query, params);
        QueryUtils.setPagination(query, request.getPage(), request.getLimit());

        List<CandidateListResponse> content = query.getResultList();
        Long totalElements = getCandidatesCount(request);

        List<CandidateResponse> candidates = new ArrayList<>();

        for (CandidateListResponse candidateListResponse : content) {
            CandidateResponse candidateResponse = new CandidateResponse();

            candidateResponse.setId(candidateListResponse.id());
            candidateResponse.setName(candidateListResponse.name());
            candidateResponse.setEmail(candidateListResponse.email());
            candidateResponse.setMobileNumber(candidateListResponse.mobileNumber());
            candidateResponse.setAlternateMobileNumber(candidateListResponse.alternateMobileNumber());
            candidateResponse.setGender(candidateListResponse.gender());
            candidateResponse.setDateOfBirth(candidateListResponse.dateOfBirth());
            candidateResponse.setCurrentCompany(candidateListResponse.currentCompany());
            candidateResponse.setCurrentDesignation(candidateListResponse.currentDesignation());
            candidateResponse.setTotalExperience(candidateListResponse.totalExperience());
            candidateResponse.setRelevantExperience(candidateListResponse.relevantExperience());
            candidateResponse.setQualification(candidateListResponse.qualification());
            candidateResponse.setCurrentCtc(candidateListResponse.currentCtc());
            candidateResponse.setExpectedCtc(candidateListResponse.expectedCtc());
            candidateResponse.setNoticePeriod(candidateListResponse.noticePeriod());
            candidateResponse.setLastWorkingDay(candidateListResponse.lastWorkingDay());
            candidateResponse.setCurrentLocation(candidateListResponse.currentLocation());
            candidateResponse.setPreferredLocation(candidateListResponse.preferredLocation());
            candidateResponse.setWorkModePreference(candidateListResponse.workModePreference());
            candidateResponse.setSource(candidateListResponse.source());
            candidateResponse.setRemarks(candidateListResponse.remarks());

            List<String> skills = StringUtils.hasText(candidateListResponse.skills())
                    ? Arrays.stream(candidateListResponse.skills().split(",")).map(String::trim).toList()
                    : List.of();
            candidateResponse.setSkills(skills);

            if (candidateListResponse.resumeId() != null) {
                CandidateResumeResponse resumeResponse = new CandidateResumeResponse();
                resumeResponse.setId(candidateListResponse.resumeId());
                resumeResponse.setFileName(candidateListResponse.resumeFileName());
                resumeResponse.setFileType(candidateListResponse.resumeFileType());
                candidateResponse.setResumeResponse(resumeResponse);
            }

            candidates.add(candidateResponse);
        }

        return new PageResponse<>(candidates, totalElements);
    }

    private Long getCandidatesCount(CandidateSearchRequest request) {

        List<Object> params = new ArrayList<>();
        String jpql = GET_CANDIDATES_COUNT.apply(request, params);

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        QueryUtils.setParameters(query, params);

        return query.getSingleResult();
    }
}
