package org.example.security.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Error error;
    private String token;

    public enum Error {
        ok,
        invalidUsername,
        usernameAlreadyExists,
        emailAlreadyExists,
        invalidEmail,
        invalidPassword,
        passwordsDoNotMatch,
        invalidCredentials

    }

    public static JwtResponse success(String token) {
        return builder().error(Error.ok).token(token).build();
    }

    public static JwtResponse failed(Error error) {
        return builder().error(error).build();
    }

}
