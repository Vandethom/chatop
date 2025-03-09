package com.chatop.api.factories;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.request.RentalUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class RentalDTOFactory {
    public RentalDTO createRentalDTO(
        String name, 
        Double surface, 
        Double price, 
        String description
        ) {
            RentalDTO dto = new RentalDTO();

            dto.setName(name);
            dto.setSurface(surface);
            dto.setPrice(price);
            dto.setDescription(description);

            return dto;
    }

    public RentalUpdateDTO createRentalUpdateDTO(
        String name, 
        Double surface, 
        Double price, 
        String description
        ) {
            RentalUpdateDTO dto = new RentalUpdateDTO();

            if (name        != null) dto.setName(name);
            if (surface     != null) dto.setSurface(surface);
            if (price       != null) dto.setPrice(price);
            if (description != null) dto.setDescription(description);

            return dto;
    }
}