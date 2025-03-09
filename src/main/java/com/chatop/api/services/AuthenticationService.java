package com.chatop.api.services;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.models.User;
import com.chatop.api.services.operations.auth.GetCurrentUserOperation;
import com.chatop.api.services.operations.auth.LoginOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final LoginOperation          loginOperation;
    private final GetCurrentUserOperation getCurrentUserOperation;
    
    @Autowired
    public AuthenticationService(
            LoginOperation          loginOperation, 
            GetCurrentUserOperation getCurrentUserOperation
            ) {
                this.loginOperation          = loginOperation;
                this.getCurrentUserOperation = getCurrentUserOperation;
            }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        return loginOperation.execute(loginDTO);
    }

    public User getCurrentUser() {
        return getCurrentUserOperation.execute(null);
    }
}