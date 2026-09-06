package com.gt.hurecom.controller.admin;

import com.gt.hurecom.dto.admin.RoleResponse;
import com.gt.hurecom.service.admin.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles() {
        log.debug("Controller :: getRoles :: Entered");

        List<RoleResponse> response = roleService.getRoles();

        log.debug("Controller :: getRoles :: Exited");
        return ResponseEntity.ok(response);
    }

}
