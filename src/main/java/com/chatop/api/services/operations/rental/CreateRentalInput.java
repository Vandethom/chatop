package com.chatop.api.services.operations.rental;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.models.User;
import org.springframework.web.multipart.MultipartFile;

public class CreateRentalInput {
    private final RentalDTO rentalDTO;
    private final MultipartFile picture;
    private final User owner;
    
    public CreateRentalInput(RentalDTO rentalDTO, MultipartFile picture, User owner) {
        this.rentalDTO = rentalDTO;
        this.picture   = picture;
        this.owner     = owner;
    }
    
    public RentalDTO getRentalDTO() {
        return rentalDTO;
    }
    
    public MultipartFile getPicture() {
        return picture;
    }
    
    public User getOwner() {
        return owner;
    }
}