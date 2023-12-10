package org.example.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("authentication")
public class AuthenticationController {

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
