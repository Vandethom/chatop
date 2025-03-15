package com.chatop.api.mappers;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Mapper(
    componentModel                   = "spring",
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
    imports                          = { Timestamp.class, Instant.class}
    )
public interface MessageMapperStruct {

    @Mapping(target = "id",     ignore        = true)
    @Mapping(target = "user",   ignore        = true)
    @Mapping(target = "rental", ignore        = true)
    @Mapping(target = "createdAt", expression = "java(Timestamp.from(Instant.now()))")
    @Mapping(target = "updatedAt", expression = "java(Timestamp.from(Instant.now()))")
    Message toEntity(MessageDTO dto);
    
    @Mapping(target = "user_id",    source = "user.id")
    @Mapping(target = "rental_id",  source = "rental.id")
    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "updated_at", source = "updatedAt")
    MessageResponseDTO toResponseDTO(Message message);

    List<MessageResponseDTO> toResponseDTOList(List<Message> messages);
    
    default Message toEntity(MessageDTO dto, User user, Rental rental) {
        Message message = toEntity(dto);

        message.setUser(user);
        message.setRental(rental);

        return message;
    }
}