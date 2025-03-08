package com.chatop.api.mappers;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.mappers.interfaces.IRentalMapper;
import com.chatop.api.models.Rental;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper implements IRentalMapper {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public Rental toEntity(RentalDTO dto) {
        Rental rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        
        return rental;
    }
    
    @Override
    public RentalResponseDTO toResponseDTO(Rental rental) {
        RentalResponseDTO dto = new RentalResponseDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwner_id(rental.getOwner().getId());
        dto.setCreated_at(rental.getCreatedAt().toLocalDateTime().format(formatter));
        dto.setUpdated_at(rental.getUpdatedAt().toLocalDateTime().format(formatter));
        return dto;
    }
    
    @Override
    public List<RentalResponseDTO> toResponseDTOList(List<Rental> rentals) {
        return rentals.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}