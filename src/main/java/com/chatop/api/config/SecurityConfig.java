package com.chatop.api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfigurationSource;

import com.chatop.api.config.security.SecurityRule;
import com.chatop.api.services.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private final JwtRequestFilter            jwtRequestFilter;
    private final CorsConfigurationSource     corsConfigurationSource;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final List<SecurityRule>          securityRules;

    public SecurityConfig(
            JwtRequestFilter            jwtRequestFilter,
            CorsConfigurationSource     corsConfigurationSource,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            List<SecurityRule>          securityRules
            ) {
                this.jwtRequestFilter            = jwtRequestFilter;
                this.corsConfigurationSource     = corsConfigurationSource;
                this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
                this.securityRules               = securityRules;
            }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> {
                // Apply all security rules
                for (SecurityRule rule : securityRules) {
                    rule.configure(authorizeRequests);
                }
                
                // Default rule
                authorizeRequests.anyRequest().authenticated();
            })
            .exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
            
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}