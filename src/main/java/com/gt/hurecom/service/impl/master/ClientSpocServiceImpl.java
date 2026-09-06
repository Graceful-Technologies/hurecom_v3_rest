package com.gt.hurecom.service.impl.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientSpocRequest;
import com.gt.hurecom.dto.master.ClientSpocResponse;
import com.gt.hurecom.dto.master.ClientSpocSearchRequest;
import com.gt.hurecom.entity.master.Client;
import com.gt.hurecom.entity.master.ClientSpoc;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.master.ClientSpocMapper;
import com.gt.hurecom.repository.master.ClientSpocRepository;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.master.ClientSpocService;
import com.gt.hurecom.specification.master.ClientSpocSpecification;
import com.gt.hurecom.utility.CommonUtils;
import com.gt.hurecom.utility.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientSpocServiceImpl implements ClientSpocService {

    private static final Logger log = LoggerFactory.getLogger(ClientSpocServiceImpl.class);

    private final ClientSpocRepository clientSpocRepository;

    private final ClientSpocMapper clientSpocMapper;

    private final ReferenceDataService referenceDataService;

    public ClientSpocServiceImpl(ClientSpocRepository clientSpocRepository,
                                 ClientSpocMapper clientSpocMapper,
                                 ReferenceDataService referenceDataService) {
        super();
        this.clientSpocRepository = clientSpocRepository;
        this.clientSpocMapper = clientSpocMapper;
        this.referenceDataService = referenceDataService;
    }

    @Override
    public ClientSpocResponse createClientSpoc(ClientSpocRequest request) throws HurecomException {
        log.debug("Service :: createClientSpoc :: Entered");

        if (clientSpocRepository.existsByEmailIgnoreCaseOrMobileNumber(request.getEmail(), request.getMobileNumber())) {
            CommonUtils.throwBusinessException("Email or Mobile number already exists.");
        }

        ClientSpoc clientSpoc = clientSpocMapper.convertToEntity(request);

        Client client = referenceDataService.getClientById(request.getClientId());
        clientSpoc.setClient(client);

        ClientSpoc savedClientSpoc = clientSpocRepository.save(clientSpoc);

        log.debug("Service :: createClientSpoc :: Exited");
        return clientSpocMapper.convertToResponse(savedClientSpoc);
    }

    @Override
    public ClientSpocResponse updateClientSpoc(Long id, ClientSpocRequest request) throws HurecomException {
        log.debug("Service :: updateClientSpoc :: Entered");

        ClientSpoc existingClientSpoc = clientSpocRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Client Spoc not found."));

        if (!existingClientSpoc.getEmail().equalsIgnoreCase(request.getEmail())
                && clientSpocRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
            CommonUtils.throwBusinessException("Email already exists.");
        }

        if (!existingClientSpoc.getMobileNumber().equalsIgnoreCase(request.getMobileNumber())
                && clientSpocRepository.existsByMobileNumberAndIdNot(request.getMobileNumber(), id)) {
            CommonUtils.throwBusinessException("Mobile number already exists.");
        }

        clientSpocMapper.updateFromRequest(request, existingClientSpoc);
        ClientSpoc savedClientSpoc = clientSpocRepository.save(existingClientSpoc);

        log.debug("Service :: updateClientSpoc :: Exited");
        return clientSpocMapper.convertToResponse(savedClientSpoc);
    }

    @Override
    public ClientSpocResponse getClientSpoc(Long id) {
        log.debug("Service :: getClientSpoc :: Entered");

        ClientSpoc clientSpoc = clientSpocRepository.findById(id).orElseThrow(() -> new HurecomException("Client spoc not found."));

        log.debug("Service :: getClientSpoc :: Exited");
        return clientSpocMapper.convertToResponse(clientSpoc);
    }

    @Override
    public List<ClientSpocResponse> getClientSpocs(Long clientId) {
        log.debug("Service :: getClientSpocs :: Entered");

        if (Objects.isNull(clientId)) {
            CommonUtils.throwBusinessException("Client ID is required.");
        }

        List<ClientSpocResponse> spocs = clientSpocRepository.findByClient_Id(clientId).stream()
                .map(clientSpocMapper::convertToResponse).toList();

        log.debug("Service :: getClientSpocs :: Exited");
        return spocs;
    }

    @Override
    public List<ClientSpocResponse> getActiveClientSpocs(Long clientId) {
        log.debug("Service :: getActiveClientSpocs :: Entered");

        if (Objects.isNull(clientId)) {
            CommonUtils.throwBusinessException("Client ID is required.");
        }

        List<ClientSpocResponse> spocs = clientSpocRepository.findByClient_IdAndActiveTrue(clientId).stream()
                .map(clientSpocMapper::convertToResponse).toList();

        log.debug("Service :: getActiveClientSpocs :: Exited");
        return spocs;
    }

    @Override
    public PageResponse<ClientSpocResponse> searchClientSpocs(ClientSpocSearchRequest request) {
        log.debug("Service :: searchClientSpocs :: Entered");

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());

        Specification<ClientSpoc> spec = Specification
                .where(ClientSpocSpecification.clientEquals(request.getClientId()))
                .and(ClientSpocSpecification.nameContains(request.getName()))
                .and(ClientSpocSpecification.emailContains(request.getEmail()))
                .and(ClientSpocSpecification.mobileNumberContains(request.getMobileNumber()))
                .and(ClientSpocSpecification.landlineNumberContains(request.getLandlineNumber()))
                .and(ClientSpocSpecification.isActive(request.getActive()));

        Page<ClientSpoc> clientSpocPage = clientSpocRepository.findAll(spec, pageable);

        log.debug("Service :: searchClientSpocs :: Exited");
        return PageUtils.convertToPageResponse(clientSpocPage, clientSpocMapper::convertToResponse);
    }
}
