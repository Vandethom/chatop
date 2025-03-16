package com.chatop.api.interfaces;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.models.User;

public interface IAuthenticationService {
    
    User            getCurrentUser();
    boolean         isAuthenticated();
    AuthResponseDTO login(LoginDTO loginDTO);
    AuthResponseDTO register(UserDTO userDTO);
}