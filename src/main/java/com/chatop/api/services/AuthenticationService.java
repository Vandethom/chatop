package com.chatop.api.services;

import com.chatop.api.models.User;
import com.chatop.api.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    private final IUserService userService;
    
    @Autowired
    public AuthenticationService(IUserService userService) {
        this.userService = userService;
    }
    
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails    userDetails    = (UserDetails) authentication.getPrincipal();
        
        return userService.findByEmail(userDetails.getUsername());
    }
}