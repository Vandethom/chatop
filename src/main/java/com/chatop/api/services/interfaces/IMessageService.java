package com.chatop.api.services.interfaces;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;

import java.util.List;

public interface IMessageService {
    void                     createMessage(MessageDTO messageDTO);
    MessageResponseDTO       getMessageById(Long id);
    List<MessageResponseDTO> getAllMessages();
}