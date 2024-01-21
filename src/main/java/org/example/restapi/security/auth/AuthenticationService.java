package org.example.restapi.security.auth;

import org.example.restapi.security.auth.request.LoginRequest;
import org.example.restapi.security.auth.request.RegisterRequest;
import org.example.restapi.security.auth.response.JwtResponse;

public interface AuthenticationService {
    JwtResponse register (RegisterRequest request);
    JwtResponse login (LoginRequest request);
}
