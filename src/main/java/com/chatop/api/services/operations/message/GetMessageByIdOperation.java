package com.chatop.api.services.operations.message;

import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.mappers.interfaces.IMessageMapper;
import com.chatop.api.models.Message;
import com.chatop.api.repositories.MessageRepository;
import com.chatop.api.services.operations.MessageOperation;
import org.springframework.stereotype.Component;

@Component
public class GetMessageByIdOperation implements MessageOperation<Long, MessageResponseDTO> {

    private final MessageRepository messageRepository;
    private final IMessageMapper messageMapper;
    
    public GetMessageByIdOperation(MessageRepository messageRepository, IMessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageResponseDTO execute(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
        return messageMapper.toResponseDTO(message);
    }
}