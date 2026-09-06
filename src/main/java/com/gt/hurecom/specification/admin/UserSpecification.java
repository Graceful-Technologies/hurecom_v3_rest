package com.gt.hurecom.specification.admin;

import com.gt.hurecom.entity.admin.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class UserSpecification {

    public static Specification<User> nameContains(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(email)) {
                return null;
            }
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<User> mobileNumberContains(String mobileNumber) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(mobileNumber)) {
                return null;
            }
            return cb.like(cb.lower(root.get("mobileNumber")), "%" + mobileNumber.toLowerCase() + "%");
        };
    }

    public static Specification<User> organizationEquals(Long organizationId) {
        return (root, query, cb) -> {
            if (Objects.isNull(organizationId)) {
                return null;
            }
            return cb.equal(root.get("organization").get("id"), organizationId);
        };
    }

    public static Specification<User> roleEquals(Long roleId) {
        return (root, query, cb) -> {
            if (Objects.isNull(roleId)) {
                return null;
            }
            return cb.equal(root.get("role").get("id"), roleId);
        };
    }

    public static Specification<User> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (Objects.isNull(active)) {
                return null;
            }
            return cb.equal(root.get("active"), active);
        };
    }
}
