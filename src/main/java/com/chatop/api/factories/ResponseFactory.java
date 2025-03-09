package com.chatop.api.factories;

import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.RentalsResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.dto.response.ValidationErrorResponseDTO;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    // ------------------------------ General ------------------------------ //
    public ResponseEntity<ValidationErrorResponseDTO> validationError(Map<String, String> errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponseDTO("Validation failed", errors));
    }

    public ResponseEntity<MessageResponseDTO> success(String message) {
        return ResponseEntity.ok(new MessageResponseDTO(message));
    }
    // ------------------------------ User ------------------------------ //
    public ResponseEntity<UserResponseDTO> successUser(UserResponseDTO user) {
        return ResponseEntity.ok(user);
    }
    
    public ResponseEntity<AuthResponseDTO> successAuth(AuthResponseDTO auth) {
        return ResponseEntity.ok(auth);
    }
    
    // ----------------------------- Rentals ----------------------------- //
    public ResponseEntity<RentalResponseDTO> successRental(RentalResponseDTO rental) {
        return ResponseEntity.ok(rental);
    }

    public ResponseEntity<RentalsResponseDTO> successRentals(List<RentalResponseDTO> rentals) {
        return ResponseEntity.ok(new RentalsResponseDTO(rentals));
    }

    public ResponseEntity<MessageResponseDTO> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponseDTO(message));
    }
    
    // ----------------------------- Messages ----------------------------- //
    public ResponseEntity<MessageResponseDTO> successMessage(MessageResponseDTO message) {
        return ResponseEntity.ok(message);
    }

    public ResponseEntity<List<MessageResponseDTO>> successMessages(List<MessageResponseDTO> messages) {
        return ResponseEntity.ok(messages);
    }
    
    public ResponseEntity<MessageResponseDTO> created(String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponseDTO(message));
    }

    public ResponseEntity<MessageResponseDTO> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponseDTO(message));
    }

    public ResponseEntity<MessageResponseDTO> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageResponseDTO(message));
    }

    public ResponseEntity<MessageResponseDTO> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDTO(message));
    }

    public ResponseEntity<MessageResponseDTO> error(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponseDTO("Error: " + message));
    }
}