package com.chatop.api.services.operations.rental;

import com.chatop.api.dto.request.RentalUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

public class UpdateRentalInput {
    private final Long            id;
    private final RentalUpdateDTO rentalDTO;
    private final MultipartFile   picture;
    
    public UpdateRentalInput(
        Long id, 
        RentalUpdateDTO rentalDTO, 
        MultipartFile picture
        ) {
            this.id        = id;
            this.rentalDTO = rentalDTO;
            this.picture   = picture;
        }
    
    public Long getId() {
        return id;
    }
    
    public RentalUpdateDTO getRentalDTO() {
        return rentalDTO;
    }
    
    public MultipartFile getPicture() {
        return picture;
    }
}