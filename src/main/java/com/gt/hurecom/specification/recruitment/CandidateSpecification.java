package com.gt.hurecom.specification.recruitment;

import com.gt.hurecom.entity.recruitment.Candidate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class CandidateSpecification {

    public static Specification<Candidate> nameContains(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Candidate> emailContains(String email) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(email)) return null;
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<Candidate> mobileNumberContains(String mobileNumber) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(mobileNumber)) return null;
            return cb.like(cb.lower(root.get("mobileNumber")), "%" + mobileNumber.toLowerCase() + "%");
        };
    }

    public static Specification<Candidate> skillsContains(String skills) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(skills)) return null;
            return cb.like(cb.lower(root.get("skills")), "%" + skills.toLowerCase() + "%");
        };
    }
}
