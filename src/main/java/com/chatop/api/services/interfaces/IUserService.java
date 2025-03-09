package com.chatop.api.services.interfaces;

import java.util.Optional;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.models.User;

public interface IUserService {
    AuthResponseDTO authenticateUser(LoginDTO loginDTO);
    AuthResponseDTO registerUser(UserDTO userDTO);
    UserResponseDTO getUserProfile(String email);
    UserResponseDTO getUserById(Long id);

    Optional<User> findByEmail(String email);
    User createUser(RegisterDTO registerDTO);
}