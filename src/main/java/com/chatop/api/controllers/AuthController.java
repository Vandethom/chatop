package com.chatop.api.controllers;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;

import com.chatop.api.factories.ResponseFactory;
import com.chatop.api.mappers.interfaces.IUserMapper;
import com.chatop.api.models.User;
import com.chatop.api.services.AuthenticationService;
import com.chatop.api.services.interfaces.IUserService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login and profile")
public class AuthController {

    private final IUserService          userService;
    private final ResponseFactory       responseFactory;
    private final AuthenticationService authService;
    private final IUserMapper           userMapper;

    @Autowired
    public AuthController(
        IUserService          userService, 
        ResponseFactory       responseFactory,
        AuthenticationService authService,
        IUserMapper           userMapper
        ) {
            this.userService     = userService;
            this.responseFactory = responseFactory;
            this.authService     = authService;
            this.userMapper      = userMapper;
    }

    @Operation(
        summary = "Register a new user", 
        description = "Creates a new user account and returns a JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully registered",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserDTO userDTO) {
        AuthResponseDTO authResponse = userService.registerUser(userDTO);

        return responseFactory.successAuth(authResponse);
    }

    @Operation(
        summary = "Log in a user", 
        description = "Authenticates a user and returns a JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully logged in",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponse = userService.authenticateUser(loginDTO);
    
        return responseFactory.successAuth(authResponse);
    }

    @Operation(
        summary = "Get current user profile", 
        description = "Returns the profile information of the currently authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        User            user    = authService.getCurrentUser();
        UserResponseDTO userDTO = userMapper.toResponseDTO(user);
        
        return responseFactory.successUser(userDTO);
    }
}