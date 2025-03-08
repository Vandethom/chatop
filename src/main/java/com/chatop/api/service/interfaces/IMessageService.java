package com.chatop.api.service.interfaces;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;

import java.util.List;

public interface IMessageService {
    void createMessage(MessageDTO messageDTO);
    List<MessageResponseDTO> getAllMessages();
    MessageResponseDTO getMessageById(Long id);
}