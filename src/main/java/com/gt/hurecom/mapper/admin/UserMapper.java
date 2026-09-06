package com.gt.hurecom.mapper.admin;

import com.gt.hurecom.dto.admin.UserRequest;
import com.gt.hurecom.dto.admin.UserResponse;
import com.gt.hurecom.entity.admin.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User convertToEntity(UserRequest request);

    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateFromRequest(UserRequest request, @MappingTarget User user);

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.description", target = "roleName")
    UserResponse convertToResponse(User user);

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.description", target = "roleName")
    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "lastModifiedUser.name", target = "lastModifiedUserName")
    UserResponse convertToResponseWithAudit(User user);
}
