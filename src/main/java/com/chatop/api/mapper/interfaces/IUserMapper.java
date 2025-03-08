package com.chatop.api.mapper.interfaces;

import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.model.User;

public interface IUserMapper extends IMapper<User, UserDTO, UserResponseDTO> {
}