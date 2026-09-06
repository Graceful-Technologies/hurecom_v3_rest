package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.dto.common.CityResponse;
import com.gt.hurecom.mapper.common.CityMapper;
import com.gt.hurecom.repository.common.CityRepository;
import com.gt.hurecom.service.common.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository,
                           CityMapper cityMapper) {
        super();
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }


    @Override
    public List<CityResponse> getCities(Long stateId) {
        log.debug("Service :: getCities :: Entered");

        List<CityResponse> cities = cityRepository.findByState_Id(stateId).stream()
                .map(cityMapper::convertToResponse).toList();

        log.debug("Service :: getCities :: Exited");
        return cities;
    }
}
