package com.gt.hurecom.controller.common;

import com.gt.hurecom.dto.common.CityResponse;
import com.gt.hurecom.service.common.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common/cities")
public class CityController {

    private static final Logger log = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getCities(@RequestParam("stateId") Long stateId) {
        log.debug("Controller :: getCities :: Entered");

        List<CityResponse> response = cityService.getCities(stateId);

        log.debug("Controller :: getCities :: Exited");
        return ResponseEntity.ok(response);
    }
}
