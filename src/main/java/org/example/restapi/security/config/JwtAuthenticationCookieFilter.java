package org.example.restapi.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.restapi.security.config.cookie.CookieServiceImpl;
import org.example.restapi.security.config.jwt.JwtServiceImpl;
import org.example.restapi.user.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationCookieFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtService;
    private final UserService userService;
    private final CookieServiceImpl cookieService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        Cookie cookie = cookieService.getCookie(request, "token");

        if (cookie != null) {
            String jwt = cookie.getValue();
            String username = jwtService.extractUsername(jwt);
            if (StringUtils.isNotEmpty(username)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (userDetails != null) {
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        context.setAuthentication(authToken);
                        SecurityContextHolder.setContext(context);

                        response.addCookie(cookieService.createCookie("token", jwt));
                    }
                } else {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
