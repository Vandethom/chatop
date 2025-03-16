package com.chatop.api.services;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.interfaces.IMessageService;
import com.chatop.api.models.User;
import com.chatop.api.services.operations.message.CreateMessageInput;
import com.chatop.api.services.operations.message.CreateMessageOperation;
import com.chatop.api.services.operations.message.GetAllMessagesOperation;
import com.chatop.api.services.operations.message.GetMessageByIdOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {

    private final CreateMessageOperation  createMessageOperation;
    private final GetMessageByIdOperation getMessageByIdOperation;
    private final GetAllMessagesOperation getAllMessagesOperation;
    private final AuthenticationService   authService;
    
    @Autowired
    public MessageService(
            CreateMessageOperation  createMessageOperation,
            GetMessageByIdOperation getMessageByIdOperation,
            GetAllMessagesOperation getAllMessagesOperation,
            AuthenticationService   authService
            ) {
                this.createMessageOperation  = createMessageOperation;
                this.getMessageByIdOperation = getMessageByIdOperation;
                this.getAllMessagesOperation = getAllMessagesOperation;
                this.authService             = authService;
            }

    @Override
    public void createMessage(
        MessageDTO messageDTO,
        User       user
        ) {
            User               currentUser = authService.getCurrentUser();
            CreateMessageInput input       = new CreateMessageInput(messageDTO, currentUser);
            
            createMessageOperation.execute(input);
        }

    @Override
    public MessageResponseDTO getMessageById(Long id) {
        return getMessageByIdOperation.execute(id);
    }

    @Override
    public List<MessageResponseDTO> getAllMessages() {
        return getAllMessagesOperation.execute(null);
    }
}