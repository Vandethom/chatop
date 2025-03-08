package com.chatop.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message response")
public class MessageResponseDTO {
    
    @Schema(description = "Message unique identifier", example = "15")
    private Long id;
    
    @Schema(description = "ID of the rental this message is about", example = "42")
    private Long rental_id;
    
    @Schema(description = "ID of the user who sent the message", example = "3")
    private Long user_id;
    
    @Schema(description = "Message content", 
            example = "I'm interested in renting this property. Is it still available?")
    private String message;
    
    @Schema(description = "Simple status message for API responses", example = "Message sent successfully")
    private String status;
    
    @Schema(description = "Creation date in ISO format", example = "2025-03-08T16:45:22.000Z")
    private String created_at;
    
    @Schema(description = "Last update date in ISO format", example = "2025-03-08T16:45:22.000Z")
    private String updated_at;

    // Constructor for simple status messages
    public MessageResponseDTO() {
    }

    public MessageResponseDTO(String status) {
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRental_id() {
        return rental_id;
    }

    public void setRental_id(Long rental_id) {
        this.rental_id = rental_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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