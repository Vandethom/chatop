package com.chatop.api.services.operations.user;

import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.exceptions.UserAlreadyExistsException;
import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import com.chatop.api.services.operations.UserOperation;
import com.chatop.api.utils.TimeUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserOperation implements UserOperation<RegisterDTO, User> {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TimeUtils       timeUtils;
    
    public CreateUserOperation(
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder,
            TimeUtils       timeUtils
            ) {
                this.userRepository  = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.timeUtils       = timeUtils;
            }

    @Override
    public User execute(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + registerDTO.getEmail() + " already exists");
        }
        
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        
        timeUtils.initializeTimestamps(user);
        return userRepository.save(user);
    }
}