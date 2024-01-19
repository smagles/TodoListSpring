package org.example.security.auth;

import lombok.RequiredArgsConstructor;
import org.example.security.auth.request.LoginRequest;
import org.example.security.auth.response.JwtResponse;
import org.example.security.auth.request.RegisterRequest;
import org.example.user.Role;
import org.example.user.User;
import org.example.security.config.jwt.JwtServiceImpl;
import org.example.user.UserRepository;
import org.example.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 255;

    private static final int MAX_USERNAME_LENGTH = 100;
    private static final int MAX_EMAIL_LENGTH = 100;

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return JwtResponse.failed(JwtResponse.Error.usernameAlreadyExists);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return JwtResponse.failed(JwtResponse.Error.emailAlreadyExists);
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return JwtResponse.failed(JwtResponse.Error.passwordsDoNotMatch);
        }
        Optional<JwtResponse.Error> validationError = validateRegistrationFields(request);

        if (validationError.isPresent()) {
            return JwtResponse.failed(validationError.get());
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userService.createNewUser(user);
        var jwtToken = jwtService.generateToken(user);
        return JwtResponse.success(jwtToken);

    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Optional<JwtResponse.Error> validationError = validateLoginFields(request);

        if (validationError.isPresent()) {
            return JwtResponse.failed(validationError.get());
        }
        User user = userService.findByUsername(request.getUsername());

        if (Objects.isNull(user)) {
            return JwtResponse.failed(JwtResponse.Error.invalidCredentials);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));

        String jwtToken = jwtService.generateToken(user);
        return JwtResponse.success(jwtToken);
    }

    private Optional<JwtResponse.Error> validateRegistrationFields(RegisterRequest request) {
        if (Objects.isNull(request.getUsername()) || request.getUsername().length() > MAX_USERNAME_LENGTH) {
            return Optional.of(JwtResponse.Error.invalidUsername);
        }
        if (Objects.isNull(request.getEmail()) || request.getEmail().length() > MAX_EMAIL_LENGTH) {
            return Optional.of(JwtResponse.Error.invalidEmail);
        }

        if (Objects.isNull(request.getPassword()) ||
                request.getPassword().length() > MAX_PASSWORD_LENGTH || request.getPassword().length() < MIN_PASSWORD_LENGTH) {
            return Optional.of(JwtResponse.Error.invalidPassword);
        }

        return Optional.empty();
    }

    private Optional<JwtResponse.Error> validateLoginFields(LoginRequest request) {
        if (Objects.isNull(request.getUsername()) || request.getUsername().length() > MAX_USERNAME_LENGTH) {
            return Optional.of(JwtResponse.Error.invalidUsername);
        }
        if (Objects.isNull(request.getPassword()) ||
                request.getPassword().length() > MAX_PASSWORD_LENGTH || request.getPassword().length() < MIN_PASSWORD_LENGTH) {
            return Optional.of(JwtResponse.Error.invalidPassword);
        }
        return Optional.empty();
    }
}
