package com.chatop.api.factories;

import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.RentalsResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseFactory {
    public ResponseEntity<Object> ok(Object body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<Object> created(Object body) {
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> badRequest(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> notFound(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> conflict(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    public ResponseEntity<Object> error(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<AuthResponseDTO> successAuth(AuthResponseDTO authResponse) {
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    public ResponseEntity<UserResponseDTO> successUser(UserResponseDTO userResponse) {
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    public ResponseEntity<MessageResponseDTO> successMessage(MessageResponseDTO message) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, List<MessageResponseDTO>>> successMessages(List<MessageResponseDTO> messages) {
        Map<String, List<MessageResponseDTO>> response = new HashMap<>();
        response.put("messages", messages);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<MessageResponseDTO> created(String message) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<RentalResponseDTO> successRental(RentalResponseDTO rental) {
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    public ResponseEntity<RentalsResponseDTO> successRentals(List<RentalResponseDTO> rentals) {
        RentalsResponseDTO response = new RentalsResponseDTO(rentals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<MessageResponseDTO> success(String message) {
        MessageResponseDTO responseDTO = new MessageResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}