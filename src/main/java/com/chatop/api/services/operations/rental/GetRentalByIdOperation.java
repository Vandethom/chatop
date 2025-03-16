package com.chatop.api.services.operations.rental;

import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.interfaces.IRentalMapper;
import com.chatop.api.models.Rental;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.services.operations.RentalOperation;
import org.springframework.stereotype.Component;

@Component
public class GetRentalByIdOperation implements RentalOperation<Long, RentalResponseDTO> {

    private final RentalRepository rentalRepository;
    private final IRentalMapper    mapper;
    
    public GetRentalByIdOperation(
        RentalRepository rentalRepository, 
        IRentalMapper mapper
        ) {
            this.rentalRepository = rentalRepository;
            this.mapper           = mapper;
        }

    @Override
    public RentalResponseDTO execute(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental", "id", id));
        
        return mapper.toResponseDTO(rental);
    }
}