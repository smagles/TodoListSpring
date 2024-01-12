package org.example.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.security.auth.dto.JwtRequest;
import org.example.security.auth.dto.JwtResponse;
import org.example.security.auth.AuthenticationService;
import org.example.security.config.cookie.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class WebAuthController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("jwtRequest", new JwtRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(JwtRequest request) {
        authenticationService.register(request);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("jwtRequest", new JwtRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(JwtRequest request, HttpServletResponse response) {
        JwtResponse jwtResponse = authenticationService.authenticate(request);
        Cookie tokenCookie = cookieService.createCookie("token", jwtResponse.getToken());
        response.addCookie(tokenCookie);
        return "redirect:/note/list";
    }
}
