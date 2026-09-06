package com.gt.hurecom.mapper.admin;

import com.gt.hurecom.dto.admin.OrganizationRequest;
import com.gt.hurecom.dto.admin.OrganizationResponse;
import com.gt.hurecom.entity.admin.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(target = "parentOrganization", ignore = true)
    Organization convertToEntity(OrganizationRequest request);

    @Mapping(target = "parentOrganization", ignore = true)
    void updateFromRequest(OrganizationRequest request, @MappingTarget Organization organization);

    @Mapping(source = "parentOrganization.id", target = "parentOrganizationId")
    OrganizationResponse convertToResponse(Organization organization);

}
