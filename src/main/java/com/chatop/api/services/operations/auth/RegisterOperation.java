package com.chatop.api.services.operations.auth;

import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.models.User;
import com.chatop.api.services.operations.AuthOperation;
import com.chatop.api.services.operations.user.CreateUserOperation;
import com.chatop.api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterOperation implements AuthOperation<UserDTO, AuthResponseDTO> {

    private final CreateUserOperation createUserOperation;
    private final JwtUtil             jwtUtil;

    @Autowired
    public RegisterOperation(
            CreateUserOperation createUserOperation,
            JwtUtil             jwtUtil
            ) {
                this.createUserOperation = createUserOperation;
                this.jwtUtil             = jwtUtil;
    }

    @Override
    public AuthResponseDTO execute(UserDTO userDTO) {
        
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail(userDTO.getEmail());
        registerDTO.setName(userDTO.getName());
        registerDTO.setPassword(userDTO.getPassword());
        
        User   user  = createUserOperation.execute(registerDTO);
        String token = jwtUtil.generateToken(user.getEmail());
        
        return new AuthResponseDTO(token);
    }
}