package com.chatop.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "User registration request")
public class UserDTO {
    
    @Schema(description = "User's full name", example = "John Doe")
    @NotBlank(message = "{NotBlank.userDTO.name}")
    private String name;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "{NotBlank.userDTO.email}")
    @Email(message = "{Email.userDTO.email}")
    private String email;
    
    @Schema(description = "User's password", example = "SecureP@ssw0rd")
    @NotBlank(message = "{NotBlank.userDTO.password}")
    @Size(min = 8, message = "{Size.userDTO.password}")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    private String password;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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