package com.chatop.api.interfaces;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.models.User;

import java.util.List;

public interface IMessageService {
    void                     createMessage(MessageDTO messageDTO, User user);
    MessageResponseDTO       getMessageById(Long id);
    List<MessageResponseDTO> getAllMessages();
}