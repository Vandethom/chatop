package com.chatop.api.services.operations.auth;

import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import com.chatop.api.services.operations.AuthOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetCurrentUserOperation implements AuthOperation<Void, User> {

    private final UserRepository userRepository;
    
    public GetCurrentUserOperation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(Void input) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            
            return userRepository.findByEmail(username).orElse(null);
        }
        
        return null;
    }
}