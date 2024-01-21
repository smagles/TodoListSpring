package org.example.restapi.security.config.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public interface CookieService {
    Cookie createCookie(String cookieName, String value);

    Cookie deleteCookie(String cookieName);

    Cookie getCookie(HttpServletRequest req, String cookieName);

}
