package com.gt.hurecom.service.impl.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientListResponse;
import com.gt.hurecom.dto.master.ClientRequest;
import com.gt.hurecom.dto.master.ClientResponse;
import com.gt.hurecom.dto.master.ClientSearchRequest;
import com.gt.hurecom.entity.master.Client;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.master.ClientMapper;
import com.gt.hurecom.repository.master.ClientLocationRepository;
import com.gt.hurecom.repository.master.ClientRepository;
import com.gt.hurecom.repository.master.ClientSpocRepository;
import com.gt.hurecom.service.master.ClientService;
import com.gt.hurecom.utility.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final ClientLocationRepository clientLocationRepository;

    private final ClientSpocRepository clientSpocRepository;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository,
                             ClientLocationRepository clientLocationRepository,
                             ClientSpocRepository clientSpocRepository,
                             ClientMapper clientMapper) {
        super();
        this.clientRepository = clientRepository;
        this.clientLocationRepository = clientLocationRepository;
        this.clientSpocRepository = clientSpocRepository;
        this.clientMapper = clientMapper;
    }


    @Override
    public ClientResponse createClient(ClientRequest request) throws HurecomException {
        log.debug("Service :: createClient :: Entered");

        if (clientRepository.existsByNameIgnoreCase(request.getName())) {
            CommonUtils.throwBusinessException("Client name already exists.");
        }

        Client client = clientMapper.convertToEntity(request);
        Client savedClient = clientRepository.save(client);

        log.debug("Service :: createClient :: Exited");
        return clientMapper.convertToResponse(savedClient);
    }

    @Override
    public ClientResponse updateClient(Long id, ClientRequest request) throws HurecomException {
        log.debug("Service :: updateClient :: Entered");

        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new HurecomException("Client not found."));

        if (clientRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            CommonUtils.throwBusinessException("Client name already exists.");
        }

        clientMapper.updateFromRequest(request, existingClient);
        Client savedClient = clientRepository.save(existingClient);

        log.debug("Service :: updateClient :: Exited");
        return clientMapper.convertToResponse(savedClient);
    }

    @Override
    public ClientResponse getClient(Long id) {
        log.debug("Service :: getClient :: Entered");

        Client client = clientRepository.getClientById(id).orElseThrow(() -> new HurecomException("Client not found."));
        ClientResponse clientResponse = clientMapper.convertToResponseWithAudit(client);

        clientResponse.setLocationCount(clientLocationRepository.countActiveLocations(id));
        clientResponse.setSpocCount(clientSpocRepository.countActiveSpocs(id));

        log.debug("Service :: getClient :: Exited");
        return clientResponse;
    }

    @Override
    public List<ClientResponse> getClients() {
        log.debug("Service :: getClients :: Entered");

        List<ClientResponse> clients = clientRepository.findAll().stream()
                .map(clientMapper::convertToResponse).toList();

        log.debug("Service :: getClients :: Exited");
        return clients;
    }

    @Override
    public List<ClientResponse> getActiveClients() {
        log.debug("Service :: getActiveClients :: Entered");

        List<ClientResponse> clients = clientRepository.findByActiveTrue().stream()
                .map(clientMapper::convertToResponse).toList();

        log.debug("Service :: getActiveClients :: Exited");
        return clients;
    }

    @Override
    public PageResponse<ClientListResponse> searchClients(ClientSearchRequest request) {
        log.debug("Service :: searchClients :: Entered");

        PageResponse<ClientListResponse> response = clientRepository.searchClients(request);

        log.debug("Service :: searchClients :: Exited");
        return response;
    }
}