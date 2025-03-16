package com.chatop.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    
    private final UserDetailsService     userDetailsService;
    private final JwtUtil                jwtUtil;
    private final SecurityContextService securityContextService;
    
    @Autowired
    public JwtRequestFilter(
            UserDetailsService     userDetailsService,
            JwtUtil                jwtUtil,
            SecurityContextService securityContextService
            ) {
                this.userDetailsService     = userDetailsService;
                this.jwtUtil                = jwtUtil;
                this.securityContextService = securityContextService;
            }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull FilterChain chain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String requestPath               = request.getRequestURI();
        String username                  = null;
        String jwt                       = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.debug("Extracted username from token: {}", username);
            } catch (Exception e) {
                logger.error("Error extracting username from token", e);
            }
        } else {
            logger.debug("No valid authorization header found for path: {}", requestPath);
        }

        if (username != null && !securityContextService.isAuthenticated()) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    securityContextService.setAuthentication(authentication);
                    logger.debug("Authentication set for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Error authenticating user: {}", username, e);
            }
        }

        chain.doFilter(request, response);
    }
}