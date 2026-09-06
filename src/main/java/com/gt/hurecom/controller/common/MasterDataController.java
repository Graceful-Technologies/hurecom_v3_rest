package com.gt.hurecom.controller.common;

import com.gt.hurecom.dto.common.MasterDataResponse;
import com.gt.hurecom.service.common.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common/master-data")
public class MasterDataController {

    private static final Logger log = LoggerFactory.getLogger(MasterDataController.class);

    private final MasterDataService masterDataService;

    public MasterDataController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    @GetMapping
    public ResponseEntity<List<MasterDataResponse>> getDataByType(@RequestParam("type") String type) {
        log.debug("Controller :: getDataByType :: Entered");

        List<MasterDataResponse> response = masterDataService.getDataByType(type);

        log.debug("Controller :: getDataByType :: Exited");
        return ResponseEntity.ok(response);
    }
}
