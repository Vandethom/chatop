package com.chatop.api.mappers;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.mappers.interfaces.IMessageMapper;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements IMessageMapper {
    
    @Override
    public Message toEntity(MessageDTO dto, User user, Rental rental) {
        Message message = new Message();
        
        message.setMessage(dto.getMessage());
        message.setUser(user);
        message.setRental(rental);
        
        return message;
    }

    @Override
    public MessageResponseDTO toResponseDTO(Message message) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.setId(message.getId());
        dto.setRental_id(message.getRental().getId());
        dto.setUser_id(message.getUser().getId());
        dto.setMessage(message.getMessage());
        dto.setCreated_at(message.getCreatedAt());
        dto.setUpdated_at(message.getUpdatedAt());
        return dto;
    }

    @Override
    public List<MessageResponseDTO> toResponseDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}