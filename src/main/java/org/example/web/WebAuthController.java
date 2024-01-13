package org.example.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.security.auth.dto.LoginRequest;
import org.example.security.auth.dto.JwtResponse;
import org.example.security.auth.AuthenticationService;
import org.example.security.auth.dto.RegisterRequest;
import org.example.security.config.cookie.CookieServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class WebAuthController {

    private final AuthenticationService authenticationService;
    private final CookieServiceImpl cookieService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(RegisterRequest request) {
        authenticationService.register(request);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(LoginRequest request, HttpServletResponse response) {
        JwtResponse jwtResponse = authenticationService.authenticate(request);
        Cookie tokenCookie = cookieService.createCookie("token", jwtResponse.getToken());
        response.addCookie(tokenCookie);
        return "redirect:/note/list";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login";
    }
}
