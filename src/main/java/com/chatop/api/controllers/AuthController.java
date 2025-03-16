package com.chatop.api.controllers;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.interfaces.IUserMapper;
import com.chatop.api.models.User;
import com.chatop.api.services.AuthenticationService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login and profile")
public class AuthController {
    private final AuthenticationService authService;
    private final IUserMapper           userMapper;

    @Autowired
    public AuthController(
        AuthenticationService authService,
        IUserMapper           userMapper
        ) {
            this.authService = authService;
            this.userMapper  = userMapper;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account and returns a JWT token")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User successfully registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid input") })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @Operation(summary = "Log in a user", description = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User successfully logged in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))), @ApiResponse(responseCode = "401", description = "Invalid credentials") })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @Operation(summary = "Get current user profile", description = "Returns the profile information of the currently authenticated user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User profile returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))), @ApiResponse(responseCode = "401", description = "Not authenticated") })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        User currentUser = authService.getCurrentUser();
        
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userMapper.toResponseDTO(currentUser));
    }
}