package com.chatop.api.dto.response;

import java.util.Map;

public class ValidationErrorResponseDTO {
    private String              message;
    private Map<String, String> errors;

    public ValidationErrorResponseDTO(
        String message, 
        Map<String, String> errors
        ) {
            this.message = message;
            this.errors  = errors;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}