package com.gt.hurecom.mapper.admin;

import com.gt.hurecom.dto.admin.TeamRequest;
import com.gt.hurecom.dto.admin.TeamResponse;
import com.gt.hurecom.entity.admin.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team convertToEntity(TeamRequest request);

    void updateFromRequest(TeamRequest request, @MappingTarget Team team);

    TeamResponse convertToResponse(Team team);

    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "lastModifiedUser.name", target = "lastModifiedUserName")
    TeamResponse convertToResponseWithAudit(Team team);

}
