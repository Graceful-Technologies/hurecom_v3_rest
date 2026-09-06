package com.gt.hurecom.specification.master;

import com.gt.hurecom.entity.master.ClientSpoc;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class ClientSpocSpecification {

    public static Specification<ClientSpoc> clientEquals(Long clientId) {
        return (root, query, cb) -> {
            if (Objects.isNull(clientId)) {
                return null;
            }
            return cb.equal(root.get("client").get("id"), clientId);
        };
    }

    public static Specification<ClientSpoc> nameContains(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<ClientSpoc> emailContains(String email) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(email)) {
                return null;
            }
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<ClientSpoc> mobileNumberContains(String mobileNumber) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(mobileNumber)) {
                return null;
            }
            return cb.like(cb.lower(root.get("mobileNumber")), "%" + mobileNumber.toLowerCase() + "%");
        };
    }

    public static Specification<ClientSpoc> landlineNumberContains(String landlineNumber) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(landlineNumber)) {
                return null;
            }
            return cb.like(cb.lower(root.get("landlineNumber")), "%" + landlineNumber.toLowerCase() + "%");
        };
    }

    public static Specification<ClientSpoc> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (Objects.isNull(active)) {
                return null;
            }
            return cb.equal(root.get("active"), active);
        };
    }
}
