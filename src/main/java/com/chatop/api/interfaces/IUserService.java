package com.chatop.api.interfaces;

import java.util.Optional;

import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.models.User;

public interface IUserService {
    UserResponseDTO getUserProfile(String email);
    UserResponseDTO getUserById(Long id);

    Optional<User> findByEmail(String email);
    User createUser(RegisterDTO registerDTO);
}