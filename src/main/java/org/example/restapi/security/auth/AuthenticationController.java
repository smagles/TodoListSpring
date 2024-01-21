package org.example.restapi.security.auth;

import lombok.RequiredArgsConstructor;
import org.example.restapi.security.auth.request.LoginRequest;
import org.example.restapi.security.auth.request.RegisterRequest;
import org.example.restapi.security.auth.response.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

}
