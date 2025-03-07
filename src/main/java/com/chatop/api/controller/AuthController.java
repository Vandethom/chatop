package com.chatop.api.controller;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.utils.JwtUtil;
import com.chatop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil     jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil     = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.authenticateUser(loginDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@RequestHeader("Authorization") String token) {
        String jwt   = token.substring(7);
        String email = jwtUtil.extractUsername(jwt);
        return ResponseEntity.ok(userService.getUserProfile(email));
    }
}