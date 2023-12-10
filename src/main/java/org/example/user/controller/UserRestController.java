package org.example.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
}
