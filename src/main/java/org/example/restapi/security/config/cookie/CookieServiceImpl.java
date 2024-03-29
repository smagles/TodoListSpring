package org.example.restapi.security.config.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public Cookie createCookie(String cookieName, String value) {
        Cookie token = new Cookie(cookieName, value);
        token.setHttpOnly(true);
        token.setMaxAge((int) 2 * 360 * 1000);
        token.setPath("/");
        return token;
    }

    @Override
    public Cookie deleteCookie(String cookieName) {
        Cookie token = new Cookie(cookieName, "");
        token.setHttpOnly(true);
        token.setMaxAge(0);
        token.setPath("/");
        return token;
    }

    @Override
    public Cookie getCookie(HttpServletRequest req, String cookieName) {
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

    public void resetToken(HttpServletResponse response) {
        Cookie c = deleteCookie("token");
        response.addCookie(c);
    }

}
