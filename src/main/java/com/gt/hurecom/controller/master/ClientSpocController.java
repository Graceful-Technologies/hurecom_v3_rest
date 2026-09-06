package com.gt.hurecom.controller.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientSpocRequest;
import com.gt.hurecom.dto.master.ClientSpocResponse;
import com.gt.hurecom.dto.master.ClientSpocSearchRequest;
import com.gt.hurecom.service.master.ClientSpocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/client-spocs")
public class ClientSpocController {

    private static final Logger log = LoggerFactory.getLogger(ClientSpocController.class);

    private final ClientSpocService clientSpocService;

    public ClientSpocController(ClientSpocService clientSpocService) {
        this.clientSpocService = clientSpocService;
    }

    @PostMapping
    public ResponseEntity<ClientSpocResponse> createClientSpoc(@RequestBody ClientSpocRequest request) {
        log.debug("Controller :: createClientSpoc :: Entered");

        ClientSpocResponse response = clientSpocService.createClientSpoc(request);

        log.debug("Controller :: createClientSpoc :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientSpocResponse> updateClientSpoc(@PathVariable Long id, @RequestBody ClientSpocRequest request) {
        log.debug("Controller :: updateClientSpoc :: Entered");

        ClientSpocResponse response = clientSpocService.updateClientSpoc(id, request);

        log.debug("Controller :: updateClientSpoc :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientSpocResponse> getClientSpoc(@PathVariable Long id) {
        log.debug("Controller :: getClientSpoc :: Entered");

        ClientSpocResponse response = clientSpocService.getClientSpoc(id);

        log.debug("Controller :: getClientSpoc :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientSpocResponse>> getClientSpocs(@RequestParam Long clientId) {
        log.debug("Controller :: getClientSpocs :: Entered");

        List<ClientSpocResponse> response = clientSpocService.getClientSpocs(clientId);

        log.debug("Controller :: getClientSpocs :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ClientSpocResponse>> getActiveClientSpocs(@RequestParam Long clientId) {
        log.debug("Controller :: getActiveClientSpocs :: Entered");

        List<ClientSpocResponse> response = clientSpocService.getActiveClientSpocs(clientId);

        log.debug("Controller :: getActiveClientSpocs :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<ClientSpocResponse>> searchClientSpocs(@RequestBody ClientSpocSearchRequest request) {
        log.debug("Controller :: searchClientSpocs :: Entered");

        PageResponse<ClientSpocResponse> response = clientSpocService.searchClientSpocs(request);

        log.debug("Controller :: searchClientSpocs :: Exited");
        return ResponseEntity.ok(response);
    }

}