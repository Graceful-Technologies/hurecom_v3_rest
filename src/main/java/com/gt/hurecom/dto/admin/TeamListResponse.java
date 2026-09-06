package com.gt.hurecom.dto.admin;

import java.time.LocalDateTime;

public record TeamListResponse (
    Long id,
    String name,
    boolean active,
    LocalDateTime createdDate,
    Long memberCount
){}
