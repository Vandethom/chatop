package com.chatop.api.services.operations.message;

import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.interfaces.IMessageMapper;
import com.chatop.api.models.Message;
import com.chatop.api.repositories.MessageRepository;
import com.chatop.api.services.operations.MessageOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllMessagesOperation implements MessageOperation<Void, List<MessageResponseDTO>> {

    private final MessageRepository messageRepository;
    private final IMessageMapper    messageMapper;

    @Autowired
    public GetAllMessagesOperation(
            MessageRepository messageRepository,
            IMessageMapper    messageMapper
    ) {
        this.messageRepository = messageRepository;
        this.messageMapper     = messageMapper;
    }

    @Override
    public List<MessageResponseDTO> execute(Void input) {
        List<Message> messages = messageRepository.findAll();
        
        return messageMapper.toResponseDTOList(messages);
    }
}