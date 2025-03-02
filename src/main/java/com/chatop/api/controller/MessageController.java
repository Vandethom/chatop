package com.chatop.api.controller;

import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody Map<String, Object> payload) {
        try {
            Long userId           = Long.valueOf(payload.get("user_id").toString());
            Long rentalId         = Long.valueOf(payload.get("rental_id").toString());
            String messageContent = payload.get("message").toString();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Rental rental = rentalRepository.findById(rentalId)
                    .orElseThrow(() -> new RuntimeException("Rental not found"));

            Message message = new Message();
            message.setUser(user);
            message.setRental(rental);
            message.setMessage(messageContent);
            
            Timestamp now = new Timestamp(System.currentTimeMillis());
            message.setCreatedAt(now);
            message.setUpdatedAt(now);

            messageRepository.save(message);

            // Return success response
            Map<String, String> response = new HashMap<>();
            response.put(
                "message", 
                "Message sent successfully"
                );
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error creating message: " + e.getMessage());
                }
    }
}