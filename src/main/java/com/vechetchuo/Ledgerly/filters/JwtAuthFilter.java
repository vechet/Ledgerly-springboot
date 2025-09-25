package com.vechetchuo.Ledgerly.filters;

import com.vechetchuo.Ledgerly.services.CustomUserDetailsService;
import com.vechetchuo.Ledgerly.services.TransactionService;
import com.vechetchuo.Ledgerly.utils.JwtUtil;
import com.vechetchuo.Ledgerly.utils.LoggerUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token provided — only block if endpoint is protected
            if (requiresAuth(request)) {
                handleStatus(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
                return;
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = authHeader.substring(7);
        try {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
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

    private void handleStatus(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"code\":%d,\"message\":\"%s\"}", status, message));
        response.getWriter().flush();
    }

    // Optional: define which paths require auth
    private boolean requiresAuth(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api/v1/auth")
                && !path.startsWith("/swagger")
                && !path.startsWith("/v3/api-docs")
                && !path.startsWith("/h2-console")
                && !path.startsWith("/reset-password")
                && !path.startsWith("/api/v1/auth/forgot-password")
                && !path.startsWith("/api/v1/auth/reset-password");
    }
}
