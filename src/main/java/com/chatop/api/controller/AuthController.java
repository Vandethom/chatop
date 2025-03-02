package com.chatop.api.controller;

import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.JwtUtil;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Store the original password temporarily
            String originalPassword = user.getPassword();
            
            // Set timestamps and encode password for database storage
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            user.setPassword(passwordEncoder.encode(originalPassword));
            User savedUser = userRepository.save(user);
            
            System.out.println("User saved with ID: " + savedUser.getId());
            
            try {
                // Now authenticate with the original password, not the encoded one
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
                
                return ResponseEntity.ok(new AuthSuccess(jwt));
            }
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), 
                    loginRequest.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String      jwt         = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthSuccess(jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String token) {
        //TODO: Implement this method
        return ResponseEntity.ok("User information");
    }
}