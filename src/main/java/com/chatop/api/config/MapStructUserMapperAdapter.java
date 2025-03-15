package com.chatop.api.config;

import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.mappers.UserMapperStruct;
import com.chatop.api.mappers.interfaces.IUserMapper;
import com.chatop.api.models.User;

import java.util.List;

public class MapStructUserMapperAdapter implements IUserMapper {

    private final UserMapperStruct mapstructMapper;

    public MapStructUserMapperAdapter(UserMapperStruct mapstructMapper) {
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public User toEntity(UserDTO dto) {
        return mapstructMapper.toEntity(dto);
    }

    @Override
    public UserResponseDTO toResponseDTO(User user) {
        return mapstructMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return mapstructMapper.toResponseDTOList(users);
    }
}