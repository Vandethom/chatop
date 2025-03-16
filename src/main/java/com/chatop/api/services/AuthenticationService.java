package com.chatop.api.services;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.interfaces.IAuthenticationService;
import com.chatop.api.models.User;
import com.chatop.api.services.operations.auth.RegisterOperation;
import com.chatop.api.services.operations.auth.LoginOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final RegisterOperation       registerOperation;
    private final LoginOperation          loginOperation;
    private final SecurityContextService  securityContextService;
    
    @Autowired
    public AuthenticationService(
        RegisterOperation           registerOperation,
            LoginOperation          loginOperation, 
            SecurityContextService  securityContextService
            ) {
                this.registerOperation       = registerOperation;
                this.loginOperation          = loginOperation;
                this.securityContextService  = securityContextService;
            }

    @Override
    public AuthResponseDTO register(UserDTO userDTO) {
        return registerOperation.execute(userDTO);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        return loginOperation.execute(loginDTO);
    }

    @Override
    public User getCurrentUser() {
        return securityContextService.getCurrentUser();
    }
    
    @Override
    public boolean isAuthenticated() {
        return securityContextService.isAuthenticated();
    }
}
