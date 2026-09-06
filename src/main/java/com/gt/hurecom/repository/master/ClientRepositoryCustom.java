package com.gt.hurecom.repository.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientListResponse;
import com.gt.hurecom.dto.master.ClientSearchRequest;

public interface ClientRepositoryCustom {

    PageResponse<ClientListResponse> searchClients(ClientSearchRequest request);
}
