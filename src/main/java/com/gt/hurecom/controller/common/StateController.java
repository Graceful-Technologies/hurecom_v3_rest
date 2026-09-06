package com.gt.hurecom.controller.common;

import com.gt.hurecom.dto.common.StateResponse;
import com.gt.hurecom.service.common.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common/states")
public class StateController {

    private static final Logger log = LoggerFactory.getLogger(StateController.class);

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<StateResponse>> getStates(@RequestParam("countryId") Long countryId) {
        log.debug("Controller :: getStates :: Entered");

        List<StateResponse> response = stateService.getStates(countryId);

        log.debug("Controller :: getStates :: Exited");
        return ResponseEntity.ok(response);
    }
}
