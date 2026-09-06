package com.gt.hurecom.specification.master;

import com.gt.hurecom.entity.master.ClientLocation;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ClientLocationSpecification {

    public static Specification<ClientLocation> clientEquals(Long clientId) {
        return (root, query, cb) -> {
            if (Objects.isNull(clientId)) {
                return null;
            }
            return cb.equal(root.get("client").get("id"), clientId);
        };
    }

    public static Specification<ClientLocation> countryEquals(Long countryId) {
        return (root, query, cb) -> {
            if (Objects.isNull(countryId)) {
                return null;
            }
            return cb.equal(cb.lower(root.get("country").get("id")), countryId);
        };
    }

    public static Specification<ClientLocation> stateEquals(Long stateId) {
        return (root, query, cb) -> {
            if (Objects.isNull(stateId)) {
                return null;
            }
            return cb.equal(cb.lower(root.get("state").get("id")), stateId);
        };
    }

    public static Specification<ClientLocation> cityEquals(Long cityId) {
        return (root, query, cb) -> {
            if (Objects.isNull(cityId)) {
                return null;
            }
            return cb.equal(cb.lower(root.get("city").get("id")), cityId);
        };
    }

    public static Specification<ClientLocation> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (Objects.isNull(active)) {
                return null;
            }
            return cb.equal(root.get("active"), active);
        };
    }
}
