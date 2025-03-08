package com.chatop.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User profile response")
public class UserResponseDTO {
    
    @Schema(description = "User's unique identifier", example = "42")
    private Long id;
    
    @Schema(description = "User's full name", example = "John Doe")
    private String name;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "Account creation date in ISO format", example = "2025-01-15T09:30:21.000Z")
    private String created_at;
    
    @Schema(description = "Account last update date in ISO format", example = "2025-03-05T14:22:43.000Z")
    private String updated_at;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}