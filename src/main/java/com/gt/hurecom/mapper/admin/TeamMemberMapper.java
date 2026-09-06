package com.gt.hurecom.mapper.admin;

import com.gt.hurecom.dto.admin.TeamMemberRequest;
import com.gt.hurecom.dto.admin.TeamMemberResponse;
import com.gt.hurecom.entity.admin.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "user", ignore = true)
    TeamMember convertToEntity(TeamMemberRequest request);

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateFromRequest(TeamMemberRequest request, @MappingTarget TeamMember teamMember);

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role.description", target = "roleName")
    TeamMemberResponse convertToResponse(TeamMember teamMember);
}
