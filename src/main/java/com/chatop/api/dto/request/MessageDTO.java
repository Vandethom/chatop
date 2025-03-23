package com.chatop.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Data transfer object for creating a new message")
public class MessageDTO {
    
    @Schema(description = "ID of the rental this message is about", example = "42")
    @NotNull(message = "{NotNull.messageDTO.rental_id}")
    private Long rental_id;
    
    @Schema(description = "ID of the user sending the message", example = "15")
    @NotNull(message = "{NotNull.messageDTO.user_id}")
    private Long user_id;
    
    @Schema(description = "Message content", example = "I'm interested in renting this property. Is it still available?")
    @NotBlank(message = "{NotBlank.messageDTO.message}")
    private String message;

    // Getters and Setters
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
}