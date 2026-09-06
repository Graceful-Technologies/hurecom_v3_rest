package com.gt.hurecom.dto.recruitment;

public record JobListResponse(
        Long id,
        Long clientId,
        String clientName,
        Long clientLocationId,
        String clientLocation,
        Long clientSpocId,
        String clientSpocName,
        String jobCode,
        String title,
        Integer openPositions,
        String jobDescription,
        Double minExperience,
        Double maxExperience,
        Double maxCtc,
        String skills,
        String employmentType,
        String workMode,
        String status
) {
}
