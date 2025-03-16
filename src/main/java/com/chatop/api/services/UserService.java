package com.chatop.api.services;

import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.interfaces.IUserService;
import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import com.chatop.api.services.operations.user.CreateUserOperation;
import com.chatop.api.services.operations.user.GetUserByIdOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final GetUserByIdOperation  getUserByIdOperation;
    private final CreateUserOperation   createUserOperation;
    private final UserRepository        userRepository;
    
    @Autowired
    public UserService(
            GetUserByIdOperation  getUserByIdOperation, 
            CreateUserOperation   createUserOperation,
            UserRepository        userRepository,
            AuthenticationService authService) {
        this.getUserByIdOperation = getUserByIdOperation;
        this.createUserOperation  = createUserOperation;
        this.userRepository       = userRepository;
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
}