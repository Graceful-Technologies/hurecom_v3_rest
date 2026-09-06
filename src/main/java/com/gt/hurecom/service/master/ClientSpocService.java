package com.gt.hurecom.service.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.*;
import com.gt.hurecom.exception.HurecomException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientSpocService {

    ClientSpocResponse createClientSpoc(ClientSpocRequest request) throws HurecomException;

    ClientSpocResponse updateClientSpoc(Long id, ClientSpocRequest request) throws HurecomException;

    ClientSpocResponse getClientSpoc(Long id);

    List<ClientSpocResponse> getClientSpocs(Long clientId);

    List<ClientSpocResponse> getActiveClientSpocs(Long clientId);

    PageResponse<ClientSpocResponse> searchClientSpocs(ClientSpocSearchRequest request);
}
