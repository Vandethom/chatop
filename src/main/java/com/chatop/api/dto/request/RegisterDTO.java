package com.chatop.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for user registration
 */
@Data
public class RegisterDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Default constructor
    public RegisterDTO() {
    }

    // Constructor with all fields
    public RegisterDTO(
        String email, 
        String name, 
        String password
        ) {
            this.email    = email;
            this.name     = name;
            this.password = password;
        }
}