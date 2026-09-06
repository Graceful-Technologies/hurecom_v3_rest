package com.gt.hurecom.utility;

import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUser();
        }

        throw new RuntimeException("Invalid authentication principal.");
    }

    public static User getCurrentUserOrNull() {
        try {
            return getCurrentUser();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static Long getCurrentUserIdOrNull() {
        try {
            return getCurrentUser().getId();
        } catch (Exception e) {
            return null;
        }
    }


    public static Organization getCurrentOrganization() {
        return getCurrentUser().getOrganization();
    }

}
