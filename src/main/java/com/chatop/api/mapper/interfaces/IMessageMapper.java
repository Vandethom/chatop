package com.chatop.api.mapper.interfaces;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;

public interface IMessageMapper {
    Message toEntity(MessageDTO dto, User user, Rental rental);
}