package com.chatop.api.mappers;

import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.mappers.interfaces.IUserMapper;
import com.chatop.api.models.User;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements IUserMapper {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Will be encoded by the service
        
        return user;
    }
    
    @Override
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreated_at(user.getCreatedAt());
        dto.setUpdated_at(user.getUpdatedAt());
        return dto;
    }
    
    @Override
    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}