package com.chatop.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        Path   uploadPath     = Paths.get(uploadDir);
        String absolutePath = uploadPath.toFile().getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + absolutePath + "/");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration               configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source        = new UrlBasedCorsConfigurationSource();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));    
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}