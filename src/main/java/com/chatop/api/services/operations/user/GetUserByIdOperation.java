package com.chatop.api.services.operations.user;

import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.mappers.interfaces.IUserMapper;
import com.chatop.api.models.User;
import com.chatop.api.repositories.UserRepository;
import com.chatop.api.services.operations.UserOperation;
import org.springframework.stereotype.Component;

@Component
public class GetUserByIdOperation implements UserOperation<Long, UserResponseDTO> {

    private final UserRepository userRepository;
    private final IUserMapper    mapper;
    
    public GetUserByIdOperation(
        UserRepository userRepository, 
        IUserMapper mapper
        ) {
            this.userRepository = userRepository;
            this.mapper         = mapper;
        }

    @Override
    public UserResponseDTO execute(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
                
        return mapper.toResponseDTO(user);
    }
}