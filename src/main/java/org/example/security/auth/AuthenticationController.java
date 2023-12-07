package org.example.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

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
