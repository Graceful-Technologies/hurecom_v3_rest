package com.gt.hurecom.mapper.recruitment;

import com.gt.hurecom.dto.recruitment.CandidateResumeResponse;
import com.gt.hurecom.entity.recruitment.CandidateResume;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateResumeMapper {

    @InheritConfiguration(name = "toBase")
    CandidateResumeResponse convertToResponse(CandidateResume candidateResume);
}
