package com.chatop.api.services.operations.rental;

import com.chatop.api.mappers.interfaces.IRentalMapper;
import com.chatop.api.models.Rental;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.services.interfaces.IFileStorageService;
import com.chatop.api.services.operations.RentalOperation;
import com.chatop.api.utils.TimeUtils;
import org.springframework.stereotype.Component;

@Component
public class CreateRentalOperation implements RentalOperation<CreateRentalInput, Void> {

    private final RentalRepository    rentalRepository;
    private final IRentalMapper       mapper;
    private final IFileStorageService fileStorageService;
    private final TimeUtils           timeUtils;
    
    public CreateRentalOperation(
            RentalRepository    rentalRepository,
            IRentalMapper       mapper,
            IFileStorageService fileStorageService,
            TimeUtils           timeUtils
            ) {
                this.rentalRepository   = rentalRepository;
                this.mapper             = mapper;
                this.fileStorageService = fileStorageService;
                this.timeUtils          = timeUtils;
            }

    @Override
    public Void execute(CreateRentalInput input) {
        Rental rental = mapper.toEntity(input.getRentalDTO());
        rental.setOwner(input.getOwner());
        
        if (input.getPicture() != null && !input.getPicture().isEmpty()) {
            String picturePath = fileStorageService.storeFile(input.getPicture());
            rental.setPicture(picturePath);
        }
        
        timeUtils.initializeTimestamps(rental);
        rentalRepository.save(rental);
        return null;
    }
}