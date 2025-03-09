package com.chatop.api.services.interfaces;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.models.User;

import java.util.List;

public interface IMessageService {
    void                     createMessage(MessageDTO messageDTO, User sender);
    MessageResponseDTO       getMessageById(Long id);
    List<MessageResponseDTO> getAllMessages();
}