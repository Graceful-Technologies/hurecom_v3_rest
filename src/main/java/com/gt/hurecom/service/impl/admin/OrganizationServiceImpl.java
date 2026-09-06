package com.gt.hurecom.service.impl.admin;

import com.gt.hurecom.dto.admin.OrganizationRequest;
import com.gt.hurecom.dto.admin.OrganizationResponse;
import com.gt.hurecom.dto.admin.OrganizationSearchRequest;
import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.admin.OrganizationMapper;
import com.gt.hurecom.repository.admin.OrganizationRepository;
import com.gt.hurecom.service.admin.OrganizationService;
import com.gt.hurecom.specification.admin.OrganizationSpecification;
import com.gt.hurecom.utility.CommonUtils;
import com.gt.hurecom.utility.PageUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        super();
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest request) throws HurecomException {
        log.debug("Service :: createOrganization :: Entered");

        if (organizationRepository.existsByNameIgnoreCase(request.getName())) {
            CommonUtils.throwBusinessException("Organization name already exists.");
        }

        Organization organization = organizationMapper.convertToEntity(request);

        if (Objects.nonNull(request.getParentOrganizationId())) {
            Organization parentOrganization = organizationRepository.findById(request.getParentOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Parent organization not found."));
            organization.setParentOrganization(parentOrganization);
        }

        Organization savedOrganization = organizationRepository.save(organization);

        log.debug("Service :: createOrganization :: Exited");
        return organizationMapper.convertToResponse(savedOrganization);
    }

    @Override
    public OrganizationResponse updateOrganization(Long id, OrganizationRequest request)
            throws HurecomException {
        log.debug("Service :: updateOrganization :: Entered");

        Organization existingOrganization = organizationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Organization not found."));

        if (organizationRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            CommonUtils.throwBusinessException("Organization name already exists.");
        }

        organizationMapper.updateFromRequest(request, existingOrganization);
        Organization savedOrganization = organizationRepository.save(existingOrganization);

        log.debug("Service :: updateOrganization :: Exited");
        return organizationMapper.convertToResponse(savedOrganization);
    }

    @Override
    public OrganizationResponse getOrganization(Long id) {
        log.debug("Service :: getOrganization :: Entered");

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Organization not found."));

        log.debug("Service :: getOrganization :: Exited");
        return organizationMapper.convertToResponse(organization);
    }

    @Override
    public List<OrganizationResponse> getOrganizations() {
        log.debug("Service :: getOrganizations :: Entered");

        List<OrganizationResponse> organizations = organizationRepository.findAll().stream()
                .map(organizationMapper::convertToResponse).toList();

        log.debug("Service :: getOrganizations :: Exited");
        return organizations;
    }

    @Override
    public PageResponse<OrganizationResponse> searchOrganizations(OrganizationSearchRequest request) {
        log.debug("Service :: searchOrganizations :: Entered");

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());

        Specification<Organization> spec = Specification
                .where(OrganizationSpecification.nameContains(request.getName()))
                .and(OrganizationSpecification.isActive(request.getActive()));

        Page<Organization> organizationPage = organizationRepository.findAll(spec, pageable);

        log.debug("Service :: searchOrganizations :: Exited");
        return PageUtils.convertToPageResponse(organizationPage, organizationMapper::convertToResponse);
    }

}
