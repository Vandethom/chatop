package com.chatop.api.services.operations.rental;

import com.chatop.api.constants.MessageConstants;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.exceptions.UnauthorizedException;
import com.chatop.api.interfaces.IFileStorageService;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.services.AuthenticationService;
import com.chatop.api.services.operations.RentalOperation;
import com.chatop.api.utils.TimeUtils;
import org.springframework.stereotype.Component;

@Component
public class UpdateRentalOperation implements RentalOperation<UpdateRentalInput, Void> {

    private final RentalRepository      rentalRepository;
    private final IFileStorageService   fileStorageService;
    private final AuthenticationService authService;
    private final TimeUtils              timeUtils;
    
    public UpdateRentalOperation(
            RentalRepository      rentalRepository,
            IFileStorageService   fileStorageService,
            AuthenticationService authService,
            TimeUtils             timeUtils
            ) {
                this.rentalRepository   = rentalRepository;
                this.fileStorageService = fileStorageService;
                this.authService        = authService;
                this.timeUtils          = timeUtils;
            }

    @Override
    public Void execute(UpdateRentalInput input) {
        User currentUser = authService.getCurrentUser();
        Rental rental    = rentalRepository.findById(input.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RENTAL_NOT_FOUND + input.getId()));

        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException(MessageConstants.UNAUTHORIZED_RENTAL_UPDATE);
        }

        if (input.getRentalDTO().getName() != null) {
            rental.setName(input.getRentalDTO().getName());
        }
        if (input.getRentalDTO().getSurface() != null) {
            rental.setSurface(input.getRentalDTO().getSurface());
        }
        if (input.getRentalDTO().getPrice() != null) {
            rental.setPrice(input.getRentalDTO().getPrice());
        }
        if (input.getRentalDTO().getDescription() != null) {
            rental.setDescription(input.getRentalDTO().getDescription());
        }
        if (input.getPicture() != null && !input.getPicture().isEmpty()) {
            String picturePath = fileStorageService.storeFile(input.getPicture());
            rental.setPicture(picturePath);
        }

        timeUtils.updateTimestamp(rental);
        rentalRepository.save(rental);
        
        return null;
    }
}