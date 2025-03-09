package com.chatop.api.mappers.interfaces;

import java.util.List;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

public interface IMessageMapper {
    Message                  toEntity(MessageDTO dto, User user, Rental rental);
    MessageResponseDTO       toResponseDTO(Message message);
    List<MessageResponseDTO> toResponseDTOList(List<Message> messages);
}