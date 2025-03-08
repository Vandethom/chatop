package com.chatop.api.service;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.exception.ResourceNotFoundException;
import com.chatop.api.mapper.interfaces.IMessageMapper;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.service.interfaces.IMessageService;
import com.chatop.api.service.interfaces.IRentalService;
import com.chatop.api.service.interfaces.IUserService;
import com.chatop.api.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;
    private final IUserService      userService;
    private final IRentalService    rentalService;
    private final IMessageMapper    mapper;
    private final TimeUtils         timeUtils;

    @Autowired
    public MessageService(
            MessageRepository messageRepository,
            IUserService      userService,
            IRentalService    rentalService,
            IMessageMapper    mapper,
            TimeUtils         timeUtils
            ) {
                this.messageRepository = messageRepository;
                this.userService       = userService;
                this.rentalService     = rentalService;
                this.mapper            = mapper;
                this.timeUtils         = timeUtils;
    }

    @Override
    public void createMessage(MessageDTO messageDTO) {
        User    user    = userService.getUserById(messageDTO.getUser_id());
        Rental  rental  = rentalService.getRentalEntityById(messageDTO.getRental_id());
        Message message = mapper.toEntity(messageDTO, user, rental);
        
        timeUtils.initializeTimestamps(message);
        
        messageRepository.save(message);
    }

    @Override
    public List<MessageResponseDTO> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(message -> new MessageResponseDTO(message.getMessage()))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponseDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
        return new MessageResponseDTO(message.getMessage());
    }
}