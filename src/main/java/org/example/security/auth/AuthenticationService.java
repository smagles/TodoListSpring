package org.example.security.auth;

import org.example.security.auth.request.LoginRequest;
import org.example.security.auth.request.RegisterRequest;
import org.example.security.auth.response.JwtResponse;

public interface AuthenticationService {
    JwtResponse register (RegisterRequest request);
    JwtResponse login (LoginRequest request);
}
