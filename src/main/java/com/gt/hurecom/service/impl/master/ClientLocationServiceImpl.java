package com.gt.hurecom.service.impl.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientLocationRequest;
import com.gt.hurecom.dto.master.ClientLocationResponse;
import com.gt.hurecom.dto.master.ClientLocationSearchRequest;
import com.gt.hurecom.entity.common.City;
import com.gt.hurecom.entity.master.Client;
import com.gt.hurecom.entity.master.ClientLocation;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.master.ClientLocationMapper;
import com.gt.hurecom.repository.master.ClientLocationRepository;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.master.ClientLocationService;
import com.gt.hurecom.specification.master.ClientLocationSpecification;
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
public class ClientLocationServiceImpl implements ClientLocationService {

    private static final Logger log = LoggerFactory.getLogger(ClientLocationServiceImpl.class);

    private final ClientLocationRepository clientLocationRepository;

    private final ClientLocationMapper clientLocationMapper;

    private final ReferenceDataService referenceDataService;

    public ClientLocationServiceImpl(ClientLocationRepository clientLocationRepository, ClientLocationMapper clientLocationMapper, ReferenceDataService referenceDataService) {
        super();
        this.clientLocationRepository = clientLocationRepository;
        this.clientLocationMapper = clientLocationMapper;
        this.referenceDataService = referenceDataService;
    }

    @Override
    public ClientLocationResponse createClientLocation(ClientLocationRequest request) throws HurecomException {
        log.debug("Service :: createClientLocation :: Entered");

        referenceDataService.validateLocationHierarchy(request.getCityId(), request.getStateId(), request.getCountryId());

        if (clientLocationRepository.existsByClient_IdAndCity_IdAndBranchNameIgnoreCase(
                request.getClientId(), request.getCityId(), request.getBranchName())) {
            CommonUtils.throwBusinessException("Branch name already exists for this client.");
        }

        ClientLocation clientLocation = clientLocationMapper.convertToEntity(request);

        Client client = referenceDataService.getClientById(request.getClientId());
        clientLocation.setClient(client);

        City city = referenceDataService.getCityById(request.getCityId());
        clientLocation.setCity(city);
        clientLocation.setState(city.getState());
        clientLocation.setCountry(city.getState().getCountry());

        ClientLocation savedClientLocation = clientLocationRepository.save(clientLocation);

        log.debug("Service :: createClientLocation :: Exited");
        return clientLocationMapper.convertToResponse(savedClientLocation);
    }

    @Override
    public ClientLocationResponse updateClientLocation(Long id, ClientLocationRequest request) throws HurecomException {
        log.debug("Service :: updateClientLocation :: Entered");

        ClientLocation existingClientLocation = clientLocationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Client location not found."));

        referenceDataService.validateLocationHierarchy(request.getCityId(), request.getStateId(), request.getCountryId());

        if (clientLocationRepository.existsByClient_IdAndCity_IdAndBranchNameIgnoreCaseAndIdNot(
                request.getClientId(), request.getCityId(), request.getBranchName(), id)) {
            CommonUtils.throwBusinessException("Branch name already exists for this client.");
        }

        clientLocationMapper.updateFromRequest(request, existingClientLocation);

        City city = referenceDataService.getCityById(request.getCityId());
        existingClientLocation.setCity(city);
        existingClientLocation.setState(city.getState());
        existingClientLocation.setCountry(city.getState().getCountry());

        ClientLocation savedClientLocation = clientLocationRepository.save(existingClientLocation);

        log.debug("Service :: updateClientLocation :: Exited");
        return clientLocationMapper.convertToResponse(savedClientLocation);
    }

    @Override
    public ClientLocationResponse getClientLocation(Long id) {
        log.debug("Service :: getClientLocation :: Entered");

        ClientLocation clientLocation = clientLocationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Client location not found."));

        log.debug("Service :: getClientLocation :: Exited");
        return clientLocationMapper.convertToResponse(clientLocation);
    }

    @Override
    public List<ClientLocationResponse> getClientLocations(Long clientId) {
        log.debug("Service :: getClientLocations :: Entered");

        if (Objects.isNull(clientId)) {
            CommonUtils.throwBusinessException("Client ID is required.");
        }

        List<ClientLocationResponse> locations = clientLocationRepository.findByClient_Id(clientId).stream()
                .map(clientLocationMapper::convertToResponse).toList();

        log.debug("Service :: getClientLocations :: Exited");
        return locations;
    }

    @Override
    public List<ClientLocationResponse> getActiveClientLocations(Long clientId) {
        log.debug("Service :: getActiveClientLocations :: Entered");

        if (Objects.isNull(clientId)) {
            CommonUtils.throwBusinessException("Client ID is required.");
        }

        List<ClientLocationResponse> locations = clientLocationRepository.findByClient_IdAndActiveTrue(clientId).stream()
                .map(clientLocationMapper::convertToResponse).toList();

        log.debug("Service :: getActiveClientLocations :: Exited");
        return locations;
    }

    @Override
    public PageResponse<ClientLocationResponse> searchClientLocations(ClientLocationSearchRequest request) {
        log.debug("Service :: searchClientLocations :: Entered");

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());

        Specification<ClientLocation> spec = Specification
                .where(ClientLocationSpecification.clientEquals(request.getClientId()))
                .and(ClientLocationSpecification.countryEquals(request.getCountryId()))
                .and(ClientLocationSpecification.stateEquals(request.getStateId()))
                .and(ClientLocationSpecification.cityEquals(request.getCityId()))
                .and(ClientLocationSpecification.isActive(request.getActive()));

        Page<ClientLocation> clientLocationPage = clientLocationRepository.findAll(spec, pageable);

        log.debug("Service :: searchClientLocations :: Exited");
        return PageUtils.convertToPageResponse(clientLocationPage, clientLocationMapper::convertToResponse);
    }
}
