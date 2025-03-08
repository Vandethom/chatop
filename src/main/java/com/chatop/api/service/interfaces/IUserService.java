package com.chatop.api.service.interfaces;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.model.User;

public interface IUserService {
    AuthResponseDTO registerUser(UserDTO userDTO);
    AuthResponseDTO authenticateUser(LoginDTO loginDTO);
    UserResponseDTO getUserProfile(String email);

    User findByEmail(String email);
    User getUserById(Long id);
}