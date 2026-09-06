package com.gt.hurecom.specification.admin;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.gt.hurecom.entity.admin.Organization;

public class OrganizationSpecification {

	public static Specification<Organization> nameContains(String name) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(name)) {
				return null;
			}
			return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
		};
	}

	public static Specification<Organization> isActive(Boolean active) {
		return (root, query, cb) -> {
			if (Objects.isNull(active)) {
				return null;
			}
			return cb.equal(root.get("active"), active);
		};
	}

}
