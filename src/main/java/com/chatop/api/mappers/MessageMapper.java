package com.chatop.api.mappers;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.mappers.interfaces.IMessageMapper;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements IMessageMapper {
    
    @Override
    public Message toEntity(MessageDTO dto, User user, Rental rental) {
        Message message = new Message();
        
        message.setMessage(dto.getMessage());
        message.setUser(user);
        message.setRental(rental);
        
        return message;
    }
}