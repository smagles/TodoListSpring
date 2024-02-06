package org.example.restapi.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.restapi.security.config.captcha.RecaptchaService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RecaptchaFilter extends OncePerRequestFilter {

    private final RecaptchaService recaptchaService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isCaptchaRequired(request)) {
            String recaptchaResponse = request.getParameter("g-recaptcha-response");
            if (recaptchaResponse != null && !recaptchaResponse.isEmpty()) {
                boolean isCaptchaValid = recaptchaService.validateCaptcha(recaptchaResponse);
                if (!isCaptchaValid) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isCaptchaRequired(HttpServletRequest request) {
        RequestMatcher registerRequestMatcher = new AntPathRequestMatcher("/register/**");
        RequestMatcher loginRequestMatcher = new AntPathRequestMatcher("/login/**");

        if (registerRequestMatcher.matches(request) || loginRequestMatcher.matches(request)) {
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                return true;
            }
        }
        return false;
    }


}
