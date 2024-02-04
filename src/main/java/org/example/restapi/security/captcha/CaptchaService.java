package org.example.restapi.security.captcha;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Data
@Service
@Log4j
public class CaptchaService {
    @Value("${google.recaptcha.secret}")
    private String secret;

    @Value("${google.recaptcha.score-threshold}")
    private double scoreThreshold;

    private static final String RECAPTCHA_VERIFY_URL
            = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String response) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            URI verifyUri = UriComponentsBuilder.fromUriString(RECAPTCHA_VERIFY_URL)
                    .queryParam("secret", secret)
                    .queryParam("response", response)
                    .build()
                    .toUri();
            CaptchaResponse captchaResponse = restTemplate.getForObject(verifyUri, CaptchaResponse.class);

            if (captchaResponse != null && captchaResponse.isSuccess()) {
                log.info("reCAPTCHA validation successful.");
                return true;
            } else {
                log.warn("reCAPTCHA validation failed" + captchaResponse.getErrorCodes());
                return false;
            }
        } catch (RestClientException e) {
            // Handle RestClientException (e.g., network issues) gracefully
            log.error("Error while validating reCAPTCHA:" + e.getMessage());
            return false;
        }
    }
}
