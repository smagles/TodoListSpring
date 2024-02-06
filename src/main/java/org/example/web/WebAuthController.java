package org.example.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.restapi.security.auth.request.LoginRequest;
import org.example.restapi.security.auth.response.JwtResponse;
import org.example.restapi.security.auth.AuthenticationServiceImpl;
import org.example.restapi.security.auth.request.RegisterRequest;
import org.example.restapi.security.config.cookie.CookieServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class WebAuthController {

    private final AuthenticationServiceImpl authenticationService;
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
        JwtResponse jwtResponse = authenticationService.login(request);
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
