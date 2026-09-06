package com.gt.hurecom.controller.common;

import com.gt.hurecom.dto.common.CountryResponse;
import com.gt.hurecom.service.common.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common/countries")
public class CountryController {

    private static final Logger log = LoggerFactory.getLogger(CountryController.class);

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<CountryResponse>> getCountries() {
        log.debug("Controller :: getCountries :: Entered");

        List<CountryResponse> response = countryService.getCountries();

        log.debug("Controller :: getCountries :: Exited");
        return ResponseEntity.ok(response);
    }

}
