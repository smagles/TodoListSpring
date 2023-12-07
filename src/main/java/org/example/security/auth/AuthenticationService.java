package org.example.security.auth;

import lombok.RequiredArgsConstructor;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.security.JwtService;
import org.example.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponse register(JwtRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userService.createNewUser(user);
        var jwtToken = jwtService.generateToken(user);
        return JwtResponse.builder()
                .token(jwtToken)
                .build();

    }

    public JwtResponse authenticate(JwtRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        UserDetails user = userService.loadUserByUsername(request.getUsername());
        String jwtToken = jwtService.generateToken(user);
        return JwtResponse.builder()
                .token(jwtToken)
                .build();
    }
}
