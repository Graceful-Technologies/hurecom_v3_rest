package com.gt.hurecom.mapper.recruitment;

import com.gt.hurecom.dto.recruitment.ApplicationRequest;
import com.gt.hurecom.dto.recruitment.ApplicationResponse;
import com.gt.hurecom.entity.recruitment.Application;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "job", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    @Mapping(target = "status", ignore = true)
    Application convertToEntity(ApplicationRequest request);

    @Mapping(target = "job", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "recruiter", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateFromRequest(ApplicationRequest request, @MappingTarget Application application);

    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "job.jobCode", target = "jobCode")
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "candidate.name", target = "candidateName")
    @Mapping(source = "candidate.email", target = "email")
    @Mapping(source = "candidate.mobileNumber", target = "mobileNumber")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "recruiter.id", target = "recruiterId")
    @Mapping(source = "recruiter.name", target = "recruiterName")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "status")
    @InheritConfiguration(name = "toBase")
    ApplicationResponse convertToResponse(Application application);
}

