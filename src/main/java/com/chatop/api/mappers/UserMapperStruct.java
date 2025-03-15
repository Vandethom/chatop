package com.chatop.api.mappers;

import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.models.User;
import org.mapstruct.*;

import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;

@Mapper(
    componentModel                   = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy             = ReportingPolicy.IGNORE
)
public interface UserMapperStruct {

    @Mapping(target = "id",        ignore     = true)  
    @Mapping(target = "createdAt", expression = "java(currentTimestamp())")
    @Mapping(target = "updatedAt", expression = "java(currentTimestamp())")
    User toEntity(UserDTO dto);

    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    UserResponseDTO toResponseDTO(User user);

    List<UserResponseDTO> toResponseDTOList(List<User> users);
    
    default Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }
    
    @Mapping(target = "id",        ignore     = true)  
    @Mapping(target = "createdAt", ignore     = true)
    @Mapping(target = "updatedAt", expression = "java(currentTimestamp())")
    void updateUserFromDto(UserDTO dto, @MappingTarget User user);
}