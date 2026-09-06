package com.gt.hurecom.config;

import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.repository.admin.UserRepository;
import com.gt.hurecom.utility.SecurityUtils;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@NullMarked
public class AuditorAwareImpl implements AuditorAware<User> {

    private static final Logger log = LoggerFactory.getLogger(AuditorAwareImpl.class);

    @Autowired
    private UserRepository userRepository;

    private static final String SYSTEM_EMAIL = "system@hurecom.com";

    @Override
    public Optional<User> getCurrentAuditor() {

        try {
            User user = SecurityUtils.getCurrentUserOrNull();

            if (user != null) {
                return Optional.of(user);
            }

        } catch (Exception e) {
            log.debug("No authenticated user found, falling back to SYSTEM user");
        }

        // fallback to SYSTEM user
        return userRepository.findByEmail(SYSTEM_EMAIL);
    }
}
