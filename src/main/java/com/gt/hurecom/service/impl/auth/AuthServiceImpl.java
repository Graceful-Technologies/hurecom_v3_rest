package com.gt.hurecom.service.impl.auth;

import com.gt.hurecom.dto.auth.LoginRequest;
import com.gt.hurecom.dto.auth.LoginResponse;
import com.gt.hurecom.security.JwtUtil;
import com.gt.hurecom.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        log.debug("AuthService :: login :: Attempting login for user: {}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        log.debug("AuthService :: login :: Authentication successful for user: {}", request.getUsername());

        String token = jwtUtil.generateToken(request.getUsername());

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        log.debug("AuthService :: login :: JWT token generated for user: {}", request.getUsername());
        return response;
    }

}
