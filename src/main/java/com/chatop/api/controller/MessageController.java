package com.chatop.api.controller;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;

import com.chatop.api.mapper.EntityMapper;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private EntityMapper mapper;

    @PostMapping
    public ResponseEntity<?> createMessage(@Valid @RequestBody MessageDTO messageDTO) {
        try {
            Long userId           = Long.valueOf(payload.get("user_id").toString());
            Long rentalId         = Long.valueOf(payload.get("rental_id").toString());
            String messageContent = payload.get("message").toString();

            User user = userRepository.findById(messageDTO.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Rental rental = rentalRepository.findById(messageDTO.getRental_id())
                    .orElseThrow(() -> new RuntimeException("Rental not found"));

            Message   message = mapper.toMessage(messageDTO, user, rental);
            Timestamp now     = new Timestamp(System.currentTimeMillis());

            message.setUser(user);
            message.setRental(rental);
            message.setMessage(messageContent);
            message.setCreatedAt(now);
            message.setUpdatedAt(now);

            messageRepository.save(message);

            return ResponseEntity.status(HttpStatus.CREATED)
                   .body(new MessageResponseDTO("Message sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new MessageResponseDTO("Error creating message: " + e.getMessage()));
        }
    }
}