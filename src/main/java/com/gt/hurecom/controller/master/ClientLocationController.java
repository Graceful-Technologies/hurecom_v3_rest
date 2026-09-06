package com.gt.hurecom.controller.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientLocationRequest;
import com.gt.hurecom.dto.master.ClientLocationResponse;
import com.gt.hurecom.dto.master.ClientLocationSearchRequest;
import com.gt.hurecom.service.master.ClientLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/client-locations")
public class ClientLocationController {

    private static final Logger log = LoggerFactory.getLogger(ClientLocationController.class);

    private final ClientLocationService clientLocationService;

    public ClientLocationController(ClientLocationService clientLocationService) {
        this.clientLocationService = clientLocationService;
    }

    @PostMapping
    public ResponseEntity<ClientLocationResponse> createClientLocation(@RequestBody ClientLocationRequest request) {
        log.debug("Controller :: createClientLocation :: Entered");

        ClientLocationResponse response = clientLocationService.createClientLocation(request);

        log.debug("Controller :: createClientLocation :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientLocationResponse> updateClientLocation(@PathVariable Long id, @RequestBody ClientLocationRequest request) {
        log.debug("Controller :: updateClientLocation :: Entered");

        ClientLocationResponse response = clientLocationService.updateClientLocation(id, request);

        log.debug("Controller :: updateClientLocation :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientLocationResponse> getClientLocation(@PathVariable Long id) {
        log.debug("Controller :: getClientLocation :: Entered");

        ClientLocationResponse response = clientLocationService.getClientLocation(id);

        log.debug("Controller :: getClientLocation :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientLocationResponse>> getClientLocations(@RequestParam Long clientId) {
        log.debug("Controller :: getClientLocations :: Entered");

        List<ClientLocationResponse> response = clientLocationService.getClientLocations(clientId);

        log.debug("Controller :: getClientLocations :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ClientLocationResponse>> getActiveClientLocations(@RequestParam Long clientId) {
        log.debug("Controller :: getActiveClientLocations :: Entered");

        List<ClientLocationResponse> response = clientLocationService.getActiveClientLocations(clientId);

        log.debug("Controller :: getActiveClientLocations :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<ClientLocationResponse>> searchClientLocations(@RequestBody ClientLocationSearchRequest request) {
        log.debug("Controller :: searchClientLocations :: Entered");

        PageResponse<ClientLocationResponse> response = clientLocationService.searchClientLocations(request);

        log.debug("Controller :: searchClientLocations :: Exited");
        return ResponseEntity.ok(response);
    }

}
