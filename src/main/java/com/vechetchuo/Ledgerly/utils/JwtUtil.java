package com.vechetchuo.Ledgerly.utils;

import com.vechetchuo.Ledgerly.models.domains.Role;
import com.vechetchuo.Ledgerly.models.domains.RoleClaim;
import com.vechetchuo.Ledgerly.models.domains.User;
import com.vechetchuo.Ledgerly.models.domains.UserRole;
import com.vechetchuo.Ledgerly.models.dtos.auth.JwtTokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Autowired
    private JwtExpirationUtil jwtExpirationUtil;

    @Value("${jwt.secret}") private String secret;
    private Key secretKey;

    @PostConstruct
    public void init() {
        secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), "HmacSHA256");
    }

    public JwtTokenResponse generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userName", user.getUsername());
        claims.put("phone", user.getPhone());
        claims.put("email", user.getEmail());
        claims.put("address", user.getAddress());
        claims.put("roles", user.getUserRoles().stream()
                .map(UserRole::getRole)              // go from UserRole to Role
                .map(Role::getName)                  // get role name
                .distinct()
                .collect(Collectors.toList()));
        claims.put("permissions", user.getUserRoles().stream()
                .map(UserRole::getRole)              // go from UserRole to Role
                .flatMap(role -> role.getRoleClaims().stream()) // get RoleClaims
                .map(RoleClaim::getClaimValue)       // get permission string
                .distinct()
                .collect(Collectors.toList()));
        return createToken(claims, user.getUsername());
    }

    private JwtTokenResponse createToken(Map<String, Object> claims, String subject) {
        Date expiryDate = jwtExpirationUtil.getTokenExpiration();
        long expiresIn = (expiryDate.getTime() - System.currentTimeMillis()) / 1000;

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new JwtTokenResponse(token, expiresIn); // expiresIn in seconds
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

