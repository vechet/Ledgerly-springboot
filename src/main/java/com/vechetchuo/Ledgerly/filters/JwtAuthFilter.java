package com.vechetchuo.Ledgerly.filters;

import com.vechetchuo.Ledgerly.services.CustomUserDetailsService;
import com.vechetchuo.Ledgerly.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List; // 👈 Make sure to import java.util.List

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    // ✅ Define all public paths. This MUST match the security config
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/reset-password",
            "/v3/api-docs", // For the API definition
            "/swagger-ui/", // For the UI assets (CSS, JS)
            "/swagger-ui.html", // For the main page
            "/h2-console/",
            "/reset-password");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ Check if the path is public FIRST
        if (isPublicPath(request)) {
            filterChain.doFilter(request, response);
            return; // Skip all token logic
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleStatus(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
            return;
        }

        String token = authHeader.substring(7);

        if (jwtUtil.isRefreshToken(token)) {
            handleStatus(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid use: refresh token can't be used to access APIs.");
            return;
        }

        try {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            handleStatus(response, HttpServletResponse.SC_FORBIDDEN, "Token expired");
        } catch (io.jsonwebtoken.JwtException ex) {
            handleStatus(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    // ✅ Helper method to check against the public path list
    private boolean isPublicPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    private void handleStatus(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"code\":%d,\"message\":\"%s\"}", status, message));
        response.getWriter().flush();
    }
}