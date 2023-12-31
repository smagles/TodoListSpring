package org.example.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.auth.service.AuthenticationService;
import org.example.security.auth.dto.JwtRequest;
import org.example.security.auth.dto.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthRestController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
            @RequestBody JwtRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(
            @RequestBody JwtRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
