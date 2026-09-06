package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.dto.common.CountryResponse;
import com.gt.hurecom.mapper.common.CountryMapper;
import com.gt.hurecom.repository.common.CountryRepository;
import com.gt.hurecom.service.common.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    public CountryServiceImpl(CountryRepository countryRepository,
                              CountryMapper countryMapper) {
        super();
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }


    @Override
    public List<CountryResponse> getCountries() {
        log.debug("Service :: getCountries :: Entered");

        List<CountryResponse> countries = countryRepository.findAll().stream()
                .map(countryMapper::convertToResponse).toList();

        log.debug("Service :: getCountries :: Exited");
        return countries;
    }

}
