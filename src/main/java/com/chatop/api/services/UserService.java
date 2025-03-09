package com.chatop.api.services;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import com.chatop.api.services.interfaces.IUserService;
import com.chatop.api.services.operations.user.CreateUserOperation;
import com.chatop.api.services.operations.user.GetUserByIdOperation;
import com.chatop.api.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final GetUserByIdOperation  getUserByIdOperation;
    private final CreateUserOperation   createUserOperation;
    private final UserRepository        userRepository;
    private final JwtUtil               jwtUtil;
    private final AuthenticationService authService;
    
    @Autowired
    public UserService(
            GetUserByIdOperation  getUserByIdOperation, 
            CreateUserOperation   createUserOperation,
            UserRepository        userRepository,
            JwtUtil               jwtUtil,
            AuthenticationService authService) {
        this.getUserByIdOperation = getUserByIdOperation;
        this.createUserOperation  = createUserOperation;
        this.userRepository       = userRepository;
        this.jwtUtil              = jwtUtil;
        this.authService          = authService;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return getUserByIdOperation.execute(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCreated_at(user.getCreatedAt());
        userResponseDTO.setUpdated_at(user.getUpdatedAt());
        
        return userResponseDTO;
    }

    @Override
    public User createUser(RegisterDTO registerDTO) {
        return createUserOperation.execute(registerDTO);
    }

    @Override
    public AuthResponseDTO registerUser(UserDTO userDTO) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail(userDTO.getEmail());
        registerDTO.setName(userDTO.getName());
        registerDTO.setPassword(userDTO.getPassword());
        
        User user = createUser(registerDTO);
        
        String token = jwtUtil.generateToken(user.getEmail());
        
        return new AuthResponseDTO(token);
    }
    
    @Override
    public AuthResponseDTO authenticateUser(LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}