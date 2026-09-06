package com.gt.hurecom.repository.impl.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientListResponse;
import com.gt.hurecom.dto.master.ClientSearchRequest;
import com.gt.hurecom.repository.master.ClientRepositoryCustom;
import com.gt.hurecom.utility.QueryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@Repository
public class ClientRepositoryImpl implements ClientRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    private static final BiFunction<ClientSearchRequest, List<Object>, String> SEARCH_CLIENTS_PARAMETERS = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        if (StringUtils.hasText(request.getName())) {
            sb.append(" AND LOWER(c.name) like LOWER(?) ");
            params.add(QueryUtils.like(request.getName()));
        }

        if (Objects.nonNull(request.getActive())) {
            sb.append(" AND c.active = ? ");
            params.add(request.getActive());
        }

        return sb.toString();
    };

    private static final BiFunction<ClientSearchRequest, List<Object>, String> SEARCH_CLIENTS = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                SELECT new com.gt.hurecom.dto.master.ClientListResponse(
                    c.id,
                    c.name,
                    c.active,
                    c.createdDate,
                    COUNT(DISTINCT cl.id),
                    COUNT(DISTINCT cs.id)
                )
                FROM Client c
                LEFT JOIN ClientLocation cl ON cl.client.id = c.id
                LEFT JOIN ClientSpoc cs ON cs.client.id = c.id
                WHERE 1 = 1
                """);

        String parameters = SEARCH_CLIENTS_PARAMETERS.apply(request, params);

        if (StringUtils.hasText(parameters)) {
            sb.append(parameters);
        }

        sb.append(" GROUP BY c.id, c.name, c.active, c.createdDate ");

        return sb.toString();
    };

    private static final BiFunction<ClientSearchRequest, List<Object>, String> GET_CLIENTS_COUNT = (
            request, params) -> {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                SELECT COUNT(DISTINCT c.id)
                FROM Client c
                WHERE 1 = 1
                """);

        String parameters = SEARCH_CLIENTS_PARAMETERS.apply(request, params);

        if (StringUtils.hasText(parameters)) {
            sb.append(parameters);
        }

        return sb.toString();
    };

    @Override
    public PageResponse<ClientListResponse> searchClients(ClientSearchRequest request) {

        List<Object> params = new ArrayList<>();
        String jpql = SEARCH_CLIENTS.apply(request, params);

        TypedQuery<ClientListResponse> query = entityManager.createQuery(jpql, ClientListResponse.class);

        QueryUtils.setParameters(query, params);
        QueryUtils.setPagination(query, request.getPage(), request.getLimit());

        List<ClientListResponse> content = query.getResultList();
        Long totalElements = getClientsCount(request);

        return new PageResponse<>(content, totalElements);
    }

    private Long getClientsCount(ClientSearchRequest request) {

        List<Object> params = new ArrayList<>();
        String jpql = GET_CLIENTS_COUNT.apply(request, params);

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        QueryUtils.setParameters(query, params);

        return query.getSingleResult();
    }
}
