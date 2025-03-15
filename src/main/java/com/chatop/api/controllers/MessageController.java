package com.chatop.api.controllers;

import com.chatop.api.constants.MessageConstants;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;

import com.chatop.api.factories.ResponseFactory;
import com.chatop.api.services.interfaces.IMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Endpoints for managing messages between users")
public class MessageController {

    private final IMessageService       messageService;
    private final ResponseFactory       responseFactory;

    @Autowired
    public MessageController(
        IMessageService       messageService,
        ResponseFactory       responseFactory
        ) {
            this.messageService  = messageService;
            this.responseFactory = responseFactory;
    }

    @Operation(
        summary = "Send a message", 
        description = "Sends a message from one user to another regarding a rental property"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Message sent successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "404", description = "User or rental not found")
    })
    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(
        @Valid @RequestBody MessageDTO messageDTO
    ) {        
        messageService.createMessage(messageDTO);
        
        return responseFactory.created(MessageConstants.MESSAGE_CREATED);
    }

    @Operation(
        summary = "Get all messages", 
        description = "Returns a list of all messages (admin only)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of messages returned"),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @GetMapping
    public ResponseEntity<Map<String, List<MessageResponseDTO>>> getAllMessages() {
        List<MessageResponseDTO> messages = messageService.getAllMessages();
        
        return responseFactory.successMessages(messages);
    }

    @Operation(
        summary = "Get message by ID", 
        description = "Returns details of a specific message (admin only)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message found"),
        @ApiResponse(responseCode = "404", description = "Message not found"),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> getMessageById(
        @Parameter(description = "ID of the message to retrieve") @PathVariable Long id
    ) {
        MessageResponseDTO message = messageService.getMessageById(id);
        
        return responseFactory.successMessage(message);
    }
}