package com.chatop.api.config;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.mappers.RentalMapperStruct;
import com.chatop.api.mappers.interfaces.IRentalMapper;
import com.chatop.api.models.Rental;

import java.util.List;

public class MapStructRentalMapperAdapter implements IRentalMapper {

    private final RentalMapperStruct mapstructMapper;

    public MapStructRentalMapperAdapter(RentalMapperStruct mapstructMapper) {
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public Rental toEntity(RentalDTO dto) {
        return mapstructMapper.toEntity(dto);
    }

    @Override
    public RentalResponseDTO toResponseDTO(Rental rental) {
        return mapstructMapper.toResponseDTO(rental);
    }

    @Override
    public List<RentalResponseDTO> toResponseDTOList(List<Rental> rentals) {
        return mapstructMapper.toResponseDTOList(rentals);
    }
}