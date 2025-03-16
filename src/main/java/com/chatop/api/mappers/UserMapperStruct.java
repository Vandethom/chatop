package com.chatop.api.mappers;

import com.chatop.api.dto.request.RegisterDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.interfaces.IUserMapper;
import com.chatop.api.models.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel                   = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy             = ReportingPolicy.IGNORE
)
public interface UserMapperStruct extends IUserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "updatedAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    User toEntity(RegisterDTO dto);
    
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    UserResponseDTO toResponseDTO(User user);
    
    List<UserResponseDTO> toResponseDTOList(List<User> users);
}