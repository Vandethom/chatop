package com.chatop.api.mapper;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.mapper.interfaces.IMessageMapper;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
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