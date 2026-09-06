package com.gt.hurecom.service.impl.admin;

import com.gt.hurecom.dto.admin.RoleResponse;
import com.gt.hurecom.repository.admin.RoleRepository;
import com.gt.hurecom.service.admin.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        super();
        this.roleRepository = roleRepository;
    }


    @Override
    public List<RoleResponse> getRoles() {
        log.debug("Service :: getRoles :: Entered");

        List<RoleResponse> roles = roleRepository.getRoles().stream()
                .map(role -> {
                    RoleResponse roleResponse = new RoleResponse();
                    roleResponse.setId(role.getId());
                    roleResponse.setName(role.getName());
                    roleResponse.setDescription(role.getDescription());
                    roleResponse.setActive(role.isActive());
                    return roleResponse;
                }).toList();

        log.debug("Service :: getRoles :: Exited");
        return roles;
    }
}
