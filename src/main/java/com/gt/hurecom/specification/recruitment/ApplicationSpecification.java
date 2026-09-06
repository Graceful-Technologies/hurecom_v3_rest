package com.gt.hurecom.specification.recruitment;

import com.gt.hurecom.entity.recruitment.Application;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class ApplicationSpecification {

    private static Join<?, ?> join(Root<Application> root, String field) {
        return root.getJoins().stream()
                .filter(join -> join.getAttribute().getName().equals(field))
                .findFirst()
                .orElseGet(() -> root.join(field, JoinType.LEFT));
    }

    public static Specification<Application> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }

    public static Specification<Application> organizationEquals(Long organizationId) {
        return (root, query, cb) -> {
            if (Objects.isNull(organizationId)) return null;
            return cb.equal(root.get("organization").get("id"), organizationId);
        };
    }

    public static Specification<Application> jobCodeContains(String jobCode) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(jobCode)) return null;
            return cb.like(cb.lower(join(root, "job").get("jobCode")),
                    "%" + jobCode.toLowerCase() + "%");
        };
    }

    public static Specification<Application> candidateNameContains(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) return null;
            return cb.like(cb.lower(join(root, "candidate").get("name")),
                    "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Application> emailContains(String email) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(email)) return null;
            return cb.like(cb.lower(join(root, "candidate").get("email")),
                    "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<Application> mobileNumberContains(String mobileNumber) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(mobileNumber)) return null;
            return cb.like(join(root, "candidate").get("mobileNumber"),
                    "%" + mobileNumber + "%");
        };
    }

    public static Specification<Application> skillsContains(String skills) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(skills)) return null;
            return cb.like(cb.lower(join(root, "candidate").get("skills")),
                    "%" + skills.toLowerCase() + "%");
        };
    }

    public static Specification<Application> statusEquals(Long statusId) {
        return (root, query, cb) -> {
            if (Objects.isNull(statusId)) return null;
            return cb.equal(join(root, "status").get("id"), statusId);
        };
    }

    public static Specification<Application> stageEquals(Long stageId) {
        return (root, query, cb) -> {
            if (Objects.isNull(stageId)) return null;
            return cb.equal(join(root, "status").join("stage", JoinType.LEFT).get("id"), stageId);
        };
    }
}
