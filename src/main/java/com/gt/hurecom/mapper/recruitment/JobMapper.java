package com.gt.hurecom.mapper.recruitment;

import com.gt.hurecom.dto.recruitment.JobRequest;
import com.gt.hurecom.dto.recruitment.JobResponse;
import com.gt.hurecom.entity.master.ClientLocation;
import com.gt.hurecom.entity.recruitment.Job;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "clientLocation", ignore = true)
    @Mapping(target = "clientSpoc", ignore = true)
    @Mapping(
            target = "skills",
            expression = "java(mapSkillsToString(request.getSkills()))"
    )
    Job convertToEntity(JobRequest request);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "clientLocation", ignore = true)
    @Mapping(target = "clientSpoc", ignore = true)
    @Mapping(
            target = "skills",
            expression = "java(mapSkillsToString(request.getSkills()))"
    )
    void updateFromRequest(JobRequest request, @MappingTarget Job job);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "clientLocation.id", target = "clientLocationId")
    @Mapping(source = "clientLocation", target = "clientLocation")
    @Mapping(source = "clientSpoc.id", target = "clientSpocId")
    @Mapping(source = "clientSpoc.name", target = "clientSpocName")
    @Mapping(
            target = "skills",
            expression = "java(mapSkillsToList(job.getSkills()))"
    )
    JobResponse convertToResponse(Job job);

    default String mapClientLocation(ClientLocation clientLocation) {
        if (clientLocation == null) {
            return null;
        }
        return clientLocation.getBranchName() + ", " + clientLocation.getCity().getName();
    }

    default List<String> mapTeams(Job job) {
        if (job.getAssignments() == null) {
            return List.of();
        }
        return job.getAssignments().stream()
                .map(a -> a.getTeam().getName())
                .distinct()
                .toList();
    }

    default String mapSkillsToString(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return null;
        }
        return String.join(",", skills);
    }

    default List<String> mapSkillsToList(String skills) {
        if (skills == null || skills.isBlank()) {
            return List.of();
        }
        return List.of(skills.split(","));
    }
}
