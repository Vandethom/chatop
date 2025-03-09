package com.chatop.api.services.operations.auth;

import com.chatop.api.dto.request.LoginDTO;
import com.chatop.api.dto.response.AuthResponseDTO;
import com.chatop.api.utils.JwtUtil;
import com.chatop.api.services.operations.AuthOperation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginOperation implements AuthOperation<LoginDTO, AuthResponseDTO> {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil               jwtUtil;
    
    public LoginOperation(
        AuthenticationManager authenticationManager, 
        JwtUtil               jwtUtil
        ) {
            this.authenticationManager = authenticationManager;
            this.jwtUtil = jwtUtil;
        }

    @Override
    public AuthResponseDTO execute(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String      username    = userDetails.getUsername();
        String      jwt         = jwtUtil.generateToken(username);
        
        return new AuthResponseDTO(jwt);
    }
}