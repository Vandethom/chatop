package com.chatop.api.controller;

import com.chatop.api.mapper.EntityMapper;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.JwtUtil;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EntityMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            User   user             = mapper.toUser(userDTO);
            String originalPassword = user.getPassword();
            
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            user.setPassword(passwordEncoder.encode(originalPassword));

            User savedUser = userRepository.save(user);
            
            System.out.println("User saved with ID: " + savedUser.getId());
            
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), originalPassword)
                );
                
                final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                final String      jwt         = jwtUtil.generateToken(userDetails.getUsername());
                
                return ResponseEntity.ok(new AuthSuccess(jwt));
            } catch (Exception authEx) {
                System.err.println("Authentication failed: " + authEx.getMessage());
                // If authentication fails, still return a token by loading user details directly
                final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                final String      jwt         = jwtUtil.generateToken(userDetails.getUsername());
                
                return ResponseEntity.ok(new AuthResponseDTO(jwt));
            }
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), 
                    loginDTO.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        final String      jwt         = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String token) {
        //TODO: Implement this method
        return ResponseEntity.ok("User information");
    }
}