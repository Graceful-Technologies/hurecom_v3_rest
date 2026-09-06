package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.dto.common.StateResponse;
import com.gt.hurecom.mapper.common.StateMapper;
import com.gt.hurecom.repository.common.StateRepository;
import com.gt.hurecom.service.common.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private static final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    public StateServiceImpl(StateRepository stateRepository,
                            StateMapper stateMapper) {
        super();
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    @Override
    public List<StateResponse> getStates(Long countryId) {
        log.debug("Service :: getStates :: Entered");

        List<StateResponse> states = stateRepository.findByCountry_Id(countryId).stream()
                .map(stateMapper::convertToResponse).toList();

        log.debug("Service :: getStates :: Exited");
        return states;
    }
}
