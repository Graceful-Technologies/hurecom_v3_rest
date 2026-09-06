package com.gt.hurecom.specification.recruitment;

import com.gt.hurecom.entity.recruitment.Job;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class JobSpecification {

    public static Specification<Job> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }

    public static Specification<Job> clientEquals(Long clientId) {
        return (root, query, cb) -> {
            if (Objects.isNull(clientId)) return null;
            return cb.equal(root.get("client").get("id"), clientId);
        };
    }

    public static Specification<Job> clientLocationEquals(Long locationId) {
        return (root, query, cb) -> {
            if (Objects.isNull(locationId)) return null;
            return cb.equal(root.get("clientLocation").get("id"), locationId);
        };
    }

    public static Specification<Job> clientSpocEquals(Long spocId) {
        return (root, query, cb) -> {
            if (Objects.isNull(spocId)) return null;
            return cb.equal(root.get("clientSpoc").get("id"), spocId);
        };
    }

    public static Specification<Job> titleContains(String title) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(title)) return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Job> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (Objects.isNull(active)) return null;
            return cb.equal(root.get("active"), active);
        };
    }
}
