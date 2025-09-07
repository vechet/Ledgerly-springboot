package com.vechetchuo.Ledgerly.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtExpirationUtil {

    @Value("${jwt.token_expiration_type}")
    private String tokenExpirationType;

    @Value("${jwt.token_expiration}")
    private int tokenExpiration;

    public Date getTokenExpiration() {
        Instant now = Instant.now();

        switch (tokenExpirationType.toLowerCase()) {
            case "s":
                return Date.from(now.plus(tokenExpiration, ChronoUnit.SECONDS));
            case "m":
                return Date.from(now.plus(tokenExpiration, ChronoUnit.MINUTES));
            case "h":
                return Date.from(now.plus(tokenExpiration, ChronoUnit.HOURS));
            case "d":
                return Date.from(now.plus(tokenExpiration, ChronoUnit.DAYS));
            default:
                throw new IllegalArgumentException(
                        "Invalid Jwt.accessTokenExpirationType. Use s, m, h, or d."
                );
        }
    }
}
