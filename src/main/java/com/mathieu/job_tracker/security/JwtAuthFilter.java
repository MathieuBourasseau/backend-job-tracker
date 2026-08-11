package com.mathieu.job_tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// Filter executed once per request, before it reaches any controller
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Runs on every single incoming request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Read the "Authorization" header sent by the front (equivalent to req.headers.authorization in Express)
        String authHeader = request.getHeader("Authorization");

        // Only continue if the header exists and follows the "Bearer <token>" format
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Remove the "Bearer " prefix (7 characters) to keep only the token itself
            String token = authHeader.substring(7);

            try {
                // Verify the token and extract the user id from it
                Long userId = jwtUtil.extractUserId(token);

                // Build an "authenticated" object for Spring Security, carrying the user id
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());

                // Store it so the rest of the request (controllers included) knows who is authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Invalid or expired token: do nothing, the request simply stays unauthenticated
                // (Spring Security will reject it later if the route requires authentication)
            }
        }

        // Let the request continue to the next step (equivalent to next() in Express)
        filterChain.doFilter(request, response);
    }
}
