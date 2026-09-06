package com.gt.hurecom.service.auth;

import com.gt.hurecom.dto.auth.LoginRequest;
import com.gt.hurecom.dto.auth.LoginResponse;

public interface AuthService {

	LoginResponse login(LoginRequest loginRequest);
}
