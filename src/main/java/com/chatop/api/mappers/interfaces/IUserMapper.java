package com.chatop.api.mappers.interfaces;

import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.models.User;

public interface IUserMapper extends IMapper<User, UserDTO, UserResponseDTO> {
}