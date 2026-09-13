package com.gt.hurecom.config;

import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.admin.Role;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.repository.admin.OrganizationRepository;
import com.gt.hurecom.repository.admin.RoleRepository;
import com.gt.hurecom.repository.admin.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initSystemUserAndRole(OrganizationRepository organizationRepository,
                                            UserRepository userRepository,
                                            PasswordEncoder passwordEncoder,
                                            RoleRepository roleRepository) {

        return args -> {

            log.info("Initializing system user and role...");

            Role systemRole = roleRepository.findByName("ROLE_SYSTEM").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_SYSTEM");
                role.setDescription("System");
                return roleRepository.save(role);
            });

            Role superAdminRole = roleRepository.findByName("ROLE_SUPER_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_SUPER_ADMIN");
                role.setDescription("Super Admin");
                return roleRepository.save(role);
            });

            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                role.setDescription("Admin");
                return roleRepository.save(role);
            });

            Role managerRole = roleRepository.findByName("ROLE_MANAGER").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_MANAGER");
                role.setDescription("Manager");
                return roleRepository.save(role);
            });

            Role leadRole = roleRepository.findByName("ROLE_LEAD").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_LEAD");
                role.setDescription("Lead");
                return roleRepository.save(role);
            });

            Role recruiterRole = roleRepository.findByName("ROLE_RECRUITER").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_RECRUITER");
                role.setDescription("Recruiter");
                return roleRepository.save(role);
            });

            String organizationName = "Graceful Technologies";

            Organization organization = organizationRepository.findByName(organizationName).orElseGet(() -> {
                Organization org = new Organization();
                org.setName(organizationName);
                org.setActive(true);
                return organizationRepository.save(org);
            });

            String systemEmail = "system@hurecom.com";

            if (userRepository.findByEmail(systemEmail).isEmpty()) {

                User systemUser = new User();
                systemUser.setEmail(systemEmail);
                systemUser.setName("System");
                systemUser.setMobileNumber("9700000000");
                systemUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                systemUser.setRole(systemRole);
                systemUser.setOrganization(null);
                systemUser.setActive(true);

                userRepository.save(systemUser);

                log.info("System user created");
            } else {
                log.info("System user already exists");
            }

            String userEmail = "kumark22258@gmail.com";

            if (userRepository.findByEmail(userEmail).isEmpty()) {

                User user = new User();
                user.setEmail(userEmail);
                user.setMobileNumber("9789868284");
                user.setName("Sathish Kumar");
                user.setPassword(passwordEncoder.encode("sathish123"));
                user.setRole(adminRole);
                user.setOrganization(organization);
                user.setActive(true);

                userRepository.save(user);

                log.info("Admin user created");
            } else {
                log.info("Admin user already exists");
            }
        };
    }
}
