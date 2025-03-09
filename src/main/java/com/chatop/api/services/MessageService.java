package com.chatop.api.services;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.models.User;
import com.chatop.api.services.interfaces.IMessageService;
import com.chatop.api.services.operations.message.CreateMessageInput;
import com.chatop.api.services.operations.message.CreateMessageOperation;
import com.chatop.api.services.operations.message.GetMessageByIdOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {

    private final CreateMessageOperation createMessageOperation;
    private final GetMessageByIdOperation getMessageByIdOperation;
    
    @Autowired
    public MessageService(
            CreateMessageOperation  createMessageOperation,
            GetMessageByIdOperation getMessageByIdOperation) {
                this.createMessageOperation  = createMessageOperation;
                this.getMessageByIdOperation = getMessageByIdOperation;
            }

    @Override
    public void createMessage(
        MessageDTO messageDTO, 
        User       sender
        ) {
            CreateMessageInput input = new CreateMessageInput(messageDTO, sender);
            createMessageOperation.execute(input);
        }

    @Override
    public MessageResponseDTO getMessageById(Long id) {
        return getMessageByIdOperation.execute(id);
    }

    @Override
    public List<MessageResponseDTO> getAllMessages() {
        // You'll need to implement this method
        // For now, let's return an empty list
        return new ArrayList<>();
    }
}