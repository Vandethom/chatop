package com.chatop.api.services.operations.message;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.models.User;

public class CreateMessageInput {
    private final MessageDTO messageDTO;
    private final User       sender;
    
    public CreateMessageInput(
        MessageDTO messageDTO, 
        User sender
        ) {
            this.messageDTO = messageDTO;
            this.sender     = sender;
        }
    
    public MessageDTO getMessageDTO() {
        return messageDTO;
    }
    
    public User getSender() {
        return sender;
    }
}