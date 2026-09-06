package com.gt.hurecom.service.admin;

import com.gt.hurecom.dto.admin.OrganizationRequest;
import com.gt.hurecom.dto.admin.OrganizationResponse;
import com.gt.hurecom.dto.admin.OrganizationSearchRequest;
import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.exception.HurecomException;

import java.util.List;

public interface OrganizationService {

    OrganizationResponse createOrganization(OrganizationRequest request) throws HurecomException;

    OrganizationResponse updateOrganization(Long id, OrganizationRequest request) throws HurecomException;

    OrganizationResponse getOrganization(Long id);

    List<OrganizationResponse> getOrganizations();

    PageResponse<OrganizationResponse> searchOrganizations(OrganizationSearchRequest request);
}
