package com.chatop.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response with JWT token")
public class AuthResponseDTO {
    
    @Schema(description = "JWT token for authenticated user", 
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ")
    private String token;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}