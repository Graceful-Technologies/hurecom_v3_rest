package com.gt.hurecom.dto.master;

import java.time.LocalDateTime;

public record ClientListResponse(
    Long id,
    String name,
    Boolean active,
    LocalDateTime createdDate,
    Long locationCount,
    Long spocCount
) {}
