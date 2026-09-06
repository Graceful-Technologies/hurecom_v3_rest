package com.gt.hurecom.controller.auth;

import com.gt.hurecom.controller.master.ClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.hurecom.dto.auth.LoginRequest;
import com.gt.hurecom.dto.auth.LoginResponse;
import com.gt.hurecom.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.debug("Controller :: login :: Entered");

        LoginResponse response = authService.login(request);

        log.debug("Controller :: login :: Exited");
        return ResponseEntity.ok(response);
    }
}
