package com.gt.hurecom.specification.master;

import com.gt.hurecom.entity.master.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class ClientSpecification {

    public static Specification<Client> nameContains(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Client> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (Objects.isNull(active)) {
                return null;
            }
            return cb.equal(root.get("active"), active);
        };
    }
}
