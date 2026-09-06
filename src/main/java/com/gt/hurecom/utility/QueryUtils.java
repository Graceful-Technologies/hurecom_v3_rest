package com.gt.hurecom.utility;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public final class QueryUtils {

    public static void setParameters(TypedQuery<?> query, List<Object> params) {
        int count = 1;
        for (Object param : params) {
            query.setParameter(count++, param);
        }
    }

    public static void setPagination(Query query, int page, int limit) {
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
    }

    public static String like(String value) {
        return "%" + value + "%";
    }
}
