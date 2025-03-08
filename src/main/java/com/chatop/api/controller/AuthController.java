package com.chatop.api.controller;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.model.User;  // Add this import
import com.chatop.api.utils.JwtUtil;
import com.chatop.api.service.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;  // Add this import
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final JwtUtil     jwtUtil;

    @Autowired
    public AuthController(IUserService userService, JwtUtil jwtUtil) {
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
    // retirer requestHeader("authorization")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails    userDetails    = (UserDetails) authentication.getPrincipal();
        String         email          = userDetails.getUsername();

        User             user       = userService.findByEmail(email);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Map to response DTO
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        if (user.getCreatedAt() != null) {
            userResponseDTO.setCreated_at(dateFormat.format(user.getCreatedAt()));
        }
        
        if (user.getUpdatedAt() != null) {
            userResponseDTO.setUpdated_at(dateFormat.format(user.getUpdatedAt()));
        }

        return ResponseEntity.ok(userResponseDTO);
    }
}
