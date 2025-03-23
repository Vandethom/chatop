package com.chatop.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User login request")
public class LoginDTO {
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "{NotBlank.loginDTO.email}")
    @Email(message = "{Email.loginDTO.email}")
    private String email;
    
    @Schema(description = "User's password", example = "SecureP@ssw0rd")
    @NotBlank(message = "{NotBlank.loginDTO.password}")
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}