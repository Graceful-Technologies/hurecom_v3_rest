package com.gt.hurecom.controller.master;

import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.dto.master.ClientListResponse;
import com.gt.hurecom.dto.master.ClientRequest;
import com.gt.hurecom.dto.master.ClientResponse;
import com.gt.hurecom.dto.master.ClientSearchRequest;
import com.gt.hurecom.service.master.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master/clients")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest request) {
        log.debug("Controller :: createClient :: Entered");

        ClientResponse response = clientService.createClient(request);

        log.debug("Controller :: createClient :: Exited");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @RequestBody ClientRequest request) {
        log.debug("Controller :: updateClient :: Entered");

        ClientResponse response = clientService.updateClient(id, request);

        log.debug("Controller :: updateClient :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {
        log.debug("Controller :: getClient :: Entered");

        ClientResponse response = clientService.getClient(id);

        log.debug("Controller :: getClient :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getClients() {
        log.debug("Controller :: getClients :: Entered");

        List<ClientResponse> response = clientService.getClients();

        log.debug("Controller :: getClients :: Exited");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ClientResponse>> getActiveClients() {
        log.debug("Controller :: getActiveClients :: Entered");

        List<ClientResponse> response = clientService.getActiveClients();

        log.debug("Controller :: getActiveClients :: Exited");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<ClientListResponse>> searchClients(@RequestBody ClientSearchRequest request) {
        log.debug("Controller :: searchClients :: Entered");

        PageResponse<ClientListResponse> response = clientService.searchClients(request);

        log.debug("Controller :: searchClients :: Exited");
        return ResponseEntity.ok(response);
    }
}
