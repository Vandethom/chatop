package com.chatop.api.mappers.interfaces;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

public interface IMessageMapper {
    Message toEntity(MessageDTO dto, User user, Rental rental);
}