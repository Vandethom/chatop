package com.chatop.api.service;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.exception.ResourceNotFoundException;
import com.chatop.api.mapper.EntityMapper;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.utils.JwtUtil;
import com.chatop.api.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository        userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService    userDetailsService;
    private final JwtUtil               jwtUtil;
    private final PasswordEncoder       passwordEncoder;
    private final EntityMapper          mapper;
    private final TimeUtils             timeUtils;

    @Autowired
    public UserService(
            UserRepository        userRepository,
            AuthenticationManager authenticationManager,
            UserDetailsService    userDetailsService,
            JwtUtil               jwtUtil,
            PasswordEncoder       passwordEncoder,
            EntityMapper          mapper,
            TimeUtils             timeUtils) {
        this.userRepository        = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService    = userDetailsService;
        this.jwtUtil               = jwtUtil;
        this.passwordEncoder       = passwordEncoder;
        this.mapper                = mapper;
        this.timeUtils             = timeUtils;
    }

    public AuthResponseDTO registerUser(UserDTO userDTO) {
        User user = mapper.toUser(userDTO);
        String originalPassword = user.getPassword();
        
        timeUtils.initializeTimestamps(user);
        user.setPassword(passwordEncoder.encode(originalPassword));
        
        userRepository.save(user);
        
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), originalPassword)
            );
        } catch (Exception ignored) {
            // Continue even if authentication fails
        }
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        
        return new AuthResponseDTO(jwt);
    }
    
    public AuthResponseDTO authenticateUser(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        
        return new AuthResponseDTO(jwt);
    }
    
    public UserResponseDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        return mapper.toUserResponseDTO(user);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}