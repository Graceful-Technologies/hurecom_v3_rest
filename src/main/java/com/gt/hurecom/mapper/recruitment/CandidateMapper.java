package com.gt.hurecom.mapper.recruitment;

import com.gt.hurecom.dto.recruitment.CandidateRequest;
import com.gt.hurecom.dto.recruitment.CandidateResponse;
import com.gt.hurecom.entity.recruitment.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "skills", expression = "java(mapSkillsToString(request.getSkills()))")
    Candidate convertToEntity(CandidateRequest request);

    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "skills", expression = "java(mapSkillsToString(request.getSkills()))")
    void updateFromRequest(CandidateRequest request, @MappingTarget Candidate candidate);

    @Mapping(target = "skills", expression = "java(mapSkillsToList(candidate.getSkills()))")
    CandidateResponse convertToResponse(Candidate candidate);

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
