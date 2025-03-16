package com.chatop.api.interfaces;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.models.Rental;

public interface IRentalMapper extends IMapper<Rental, RentalDTO, RentalResponseDTO> {
}