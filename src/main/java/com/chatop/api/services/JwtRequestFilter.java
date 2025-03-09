package com.chatop.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.api.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component

public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(
        UserDetailsService userDetailsService, 
        JwtUtil            jwtUtil
        ) {
            this.userDetailsService = userDetailsService;
            this.jwtUtil            = jwtUtil;
        }

    @Override
    protected void doFilterInternal(
    @NonNull HttpServletRequest request, 
    @NonNull HttpServletResponse response, 
    @NonNull FilterChain         chain
    ) throws ServletException, IOException {

        final String requestPath = request.getServletPath();
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        System.out.println("DEBUG: Processing request to: " + requestPath);

        if (requestPath.equals("/api/auth/register") || requestPath.equals("/api/auth/login")) {
            System.out.println("DEBUG: Skipping JWT validation for public endpoint");
            chain.doFilter(request, response);
            return;
        }

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
                System.out.println("DEBUG: Extracted username from JWT: " + username);
            } else {
                System.out.println("DEBUG: No valid Authorization header found");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("DEBUG: Authentication set in SecurityContext");
                }
            }

            // Make sure to continue the filter chain
            chain.doFilter(request, response);
            System.out.println("DEBUG: Filter chain completed for " + requestPath);
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in JWT filter: " + e.getMessage());
            e.printStackTrace();
            // Forward the exception rather than swallowing it
            throw new ServletException("Authentication error", e);
        }
    }
}