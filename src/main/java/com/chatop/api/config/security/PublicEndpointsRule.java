package com.chatop.api.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PublicEndpointsRule implements SecurityRule {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers(
                "/api/auth/register",
                "/api/auth/login"
            ).permitAll();
    }
}