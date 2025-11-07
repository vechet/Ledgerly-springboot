package com.vechetchuo.Ledgerly.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtExpirationUtil {

    @Value("${jwt.access_token_expiration_type}")
    private String accessTokenExpirationType;

    @Value("${jwt.access_token_expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.refresh_token_expiration_type}")
    private String refreshTokenExpirationType;

    @Value("${jwt.refresh_token_expiration}")
    private int refreshTokenExpiration;

    public Date getTokenExpiration() {
        Instant now = Instant.now();

        switch (accessTokenExpirationType.toLowerCase()) {
            case "s":
                return Date.from(now.plus(accessTokenExpiration, ChronoUnit.SECONDS));
            case "m":
                return Date.from(now.plus(accessTokenExpiration, ChronoUnit.MINUTES));
            case "h":
                return Date.from(now.plus(accessTokenExpiration, ChronoUnit.HOURS));
            case "d":
                return Date.from(now.plus(accessTokenExpiration, ChronoUnit.DAYS));
            default:
                throw new IllegalArgumentException(
                        "Invalid Jwt.accessTokenExpirationType. Use s, m, h, or d."
                );
        }
    }

    public Date getRefreshTokenExpiration() {
        Instant now = Instant.now();

        switch (refreshTokenExpirationType.toLowerCase()) {
            case "s":
                return Date.from(now.plus(refreshTokenExpiration, ChronoUnit.SECONDS));
            case "m":
                return Date.from(now.plus(refreshTokenExpiration, ChronoUnit.MINUTES));
            case "h":
                return Date.from(now.plus(refreshTokenExpiration, ChronoUnit.HOURS));
            case "d":
                return Date.from(now.plus(refreshTokenExpiration, ChronoUnit.DAYS));
            default:
                throw new IllegalArgumentException(
                        "Invalid Jwt.refreshTokenExpirationType. Use s, m, h, or d."
                );
        }
    }
}
