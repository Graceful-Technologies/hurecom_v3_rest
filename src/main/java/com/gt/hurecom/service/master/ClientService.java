package com.gt.hurecom.service.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientListResponse;
import com.gt.hurecom.dto.master.ClientRequest;
import com.gt.hurecom.dto.master.ClientResponse;
import com.gt.hurecom.dto.master.ClientSearchRequest;
import com.gt.hurecom.exception.HurecomException;

import java.util.List;

public interface ClientService {

    ClientResponse createClient(ClientRequest request) throws HurecomException;

    ClientResponse updateClient(Long id, ClientRequest request) throws HurecomException;

    ClientResponse getClient(Long id);

    List<ClientResponse> getClients();

    List<ClientResponse> getActiveClients();

    PageResponse<ClientListResponse> searchClients(ClientSearchRequest request);

}
