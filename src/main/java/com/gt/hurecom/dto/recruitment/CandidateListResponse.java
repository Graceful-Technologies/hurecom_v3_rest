package com.gt.hurecom.dto.recruitment;

import java.time.LocalDate;
import java.util.List;

public record CandidateListResponse(
        Long id,
        String name,
        String email,
        String mobileNumber,
        String alternateMobileNumber,
        String gender,
        LocalDate dateOfBirth,
        String currentCompany,
        String currentDesignation,
        Double totalExperience,
        Double relevantExperience,
        String skills,
        String qualification,
        Double currentCtc,
        Double expectedCtc,
        String noticePeriod,
        LocalDate lastWorkingDay,
        String currentLocation,
        String preferredLocation,
        String workModePreference,
        String source,
        String remarks,

        Long resumeId,
        String resumeFileName,
        String resumeFilePath,
        String resumeFileType,
        Long resumeFileSize
) {
}
