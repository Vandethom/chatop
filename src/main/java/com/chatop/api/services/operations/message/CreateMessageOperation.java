package com.chatop.api.services.operations.message;

import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.repositories.MessageRepository;
import com.chatop.api.services.operations.MessageOperation;
import com.chatop.api.services.RentalService;
import com.chatop.api.utils.TimeUtils;
import org.springframework.stereotype.Component;

@Component
public class CreateMessageOperation implements MessageOperation<CreateMessageInput, Void> {

    private final MessageRepository messageRepository;
    private final RentalService     rentalService;
    private final TimeUtils         timeUtils;
    
    public CreateMessageOperation(
            MessageRepository messageRepository,
            RentalService     rentalService,
            TimeUtils         timeUtils
            ) {
        this.messageRepository = messageRepository;
        this.rentalService     = rentalService;
        this.timeUtils         = timeUtils;
    }

    @Override
    public Void execute(CreateMessageInput input) {
        Rental rental = rentalService.getRentalEntityById(input.getMessageDTO().getRental_id());
        if (rental == null) {
            throw new ResourceNotFoundException(
                "Rental", 
                   "id", 
                input.getMessageDTO().getRental_id()
                );
        }
        
        Message message = new Message();
        message.setRental(rental);
        message.setUser(input.getSender());
        message.setMessage(input.getMessageDTO().getMessage());
        
        timeUtils.initializeTimestamps(message);
        messageRepository.save(message);
        
        return null;
    }
}