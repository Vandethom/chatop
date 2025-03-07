package com.chatop.api.controller;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(@Valid @RequestBody MessageDTO messageDTO) {
        messageService.createMessage(messageDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponseDTO("Message sent successfully"));
    }
}