package com.chatop.api.services.operations.rental;

import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.mappers.interfaces.IRentalMapper;
import com.chatop.api.models.Rental;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.services.operations.RentalOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllRentalsOperation implements RentalOperation<Void, List<RentalResponseDTO>> {

    private final RentalRepository rentalRepository;
    private final IRentalMapper    mapper;
    
    public GetAllRentalsOperation(
        RentalRepository rentalRepository, 
        IRentalMapper mapper
        ) {
            this.rentalRepository = rentalRepository;
            this.mapper           = mapper;
        }

    @Override
    public List<RentalResponseDTO> execute(Void input) {
        List<Rental> rentals = rentalRepository.findAll();
        
        return mapper.toResponseDTOList(rentals);
    }
}