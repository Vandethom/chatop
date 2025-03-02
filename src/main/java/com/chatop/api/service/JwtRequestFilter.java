package com.chatop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

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
        JwtUtil            jwtUtil) {
            this.userDetailsService = userDetailsService;
            this.jwtUtil            = jwtUtil;
        }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest  request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain         chain
        ) throws ServletException, IOException {

            final String authorizationHeader = request.getHeader("Authorization");
            String       username            = null;
            String       jwt                 = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                try {
                    username = jwtUtil.extractUsername(jwt);
                } catch (ExpiredJwtException e) {
                    response.setStatus(401);
                    response.getWriter().write("JWT Token has expired");

                    return;
                }
            }

            if (
                username != null 
             && SecurityContextHolder.getContext().getAuthentication() == null
             ) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

                    org.springframework.security.authentication.UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);
        }
}