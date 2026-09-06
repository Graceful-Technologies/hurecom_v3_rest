package com.gt.hurecom.controller.admin;

import com.gt.hurecom.dto.admin.OrganizationRequest;
import com.gt.hurecom.dto.admin.OrganizationResponse;
import com.gt.hurecom.dto.admin.OrganizationSearchRequest;
import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.service.admin.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/organizations")
public class OrganizationController {

    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestBody OrganizationRequest request) {
        log.debug("Controller :: createOrganization :: Entered");

        OrganizationResponse response = organizationService.createOrganization(request);

        log.debug("Controller :: createOrganization :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponse> updateOrganization(@PathVariable Long id,
                                                                   @RequestBody OrganizationRequest request) {
        log.debug("Controller :: updateOrganization :: Entered");

        OrganizationResponse response = organizationService.updateOrganization(id, request);

        log.debug("Controller :: updateOrganization :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganization(@PathVariable Long id) {
        log.debug("Controller :: getOrganizationById :: Entered");

        OrganizationResponse response = organizationService.getOrganization(id);

        log.debug("Controller :: getOrganization :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> getOrganizations() {
        log.debug("Controller :: getOrganizations :: Entered");

        List<OrganizationResponse> response = organizationService.getOrganizations();

        log.debug("Controller :: getOrganizations :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<OrganizationResponse>> searchOrganizations(
            @RequestBody OrganizationSearchRequest request) {
        log.debug("Controller :: searchOrganizations :: Entered");

        PageResponse<OrganizationResponse> response = organizationService.searchOrganizations(request);

        log.debug("Controller :: searchOrganizations :: Exited");
        return ResponseEntity.ok(response);
    }
}
