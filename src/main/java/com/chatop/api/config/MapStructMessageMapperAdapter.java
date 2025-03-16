package com.chatop.api.config;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.interfaces.IMessageMapper;
import com.chatop.api.mappers.MessageMapperStruct;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import java.util.List;

public class MapStructMessageMapperAdapter implements IMessageMapper {

    private final MessageMapperStruct mapstructMapper;

    public MapStructMessageMapperAdapter(MessageMapperStruct mapstructMapper) {
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public Message toEntity(MessageDTO dto, User user, Rental rental) {
        return mapstructMapper.toEntity(dto, user, rental);
    }

    @Override
    public MessageResponseDTO toResponseDTO(Message message) {
        return mapstructMapper.toResponseDTO(message);
    }

    @Override
    public List<MessageResponseDTO> toResponseDTOList(List<Message> messages) {
        return mapstructMapper.toResponseDTOList(messages);
    }
}