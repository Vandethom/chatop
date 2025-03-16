package com.chatop.api.services.operations.auth;

import com.chatop.api.models.User;
import com.chatop.api.services.SecurityContextService;
import com.chatop.api.services.operations.AuthOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCurrentUserOperation implements AuthOperation<Void, User> {

    private final SecurityContextService securityContextService;
    
    @Autowired
    public GetCurrentUserOperation(SecurityContextService securityContextService) {
        this.securityContextService = securityContextService;
    }

    @Override
    public User execute(Void input) {
        return securityContextService.getCurrentUser();
    }
}