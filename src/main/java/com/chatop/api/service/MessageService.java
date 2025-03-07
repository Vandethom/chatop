package com.chatop.api.service;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.exception.ResourceNotFoundException;
import com.chatop.api.mapper.EntityMapper;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService       userService;
    private final RentalService     rentalService;
    private final EntityMapper      mapper;
    private final TimeUtils         timeUtils;

    @Autowired
    public MessageService(
            MessageRepository messageRepository,
            UserService       userService,
            RentalService     rentalService,
            EntityMapper      mapper,
            TimeUtils         timeUtils) {
        this.messageRepository = messageRepository;
        this.userService       = userService;
        this.rentalService     = rentalService;
        this.mapper            = mapper;
        this.timeUtils         = timeUtils;
    }

    public void createMessage(MessageDTO messageDTO) {
        User user     = userService.getUserById(messageDTO.getUser_id());
        Rental rental = rentalRepository.findById(messageDTO.getRental_id())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + messageDTO.getRental_id()));
        
        Message message = mapper.toMessage(messageDTO, user, rental);
        timeUtils.initializeTimestamps(message);
        
        messageRepository.save(message);
    }
}