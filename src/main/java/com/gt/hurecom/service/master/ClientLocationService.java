package com.gt.hurecom.service.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientLocationRequest;
import com.gt.hurecom.dto.master.ClientLocationResponse;
import com.gt.hurecom.dto.master.ClientLocationSearchRequest;
import com.gt.hurecom.exception.HurecomException;

import java.util.List;

public interface ClientLocationService {

    ClientLocationResponse createClientLocation(ClientLocationRequest request) throws HurecomException;

    ClientLocationResponse updateClientLocation(Long id, ClientLocationRequest request) throws HurecomException;

    ClientLocationResponse getClientLocation(Long id);

    List<ClientLocationResponse> getClientLocations(Long clientId);

    List<ClientLocationResponse> getActiveClientLocations(Long clientId);

    PageResponse<ClientLocationResponse> searchClientLocations(ClientLocationSearchRequest request);

}
