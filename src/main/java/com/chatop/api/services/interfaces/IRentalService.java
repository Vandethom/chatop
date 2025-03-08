package com.chatop.api.services.interfaces;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRentalService {
    List<RentalResponseDTO> getAllRentals();
    RentalResponseDTO getRentalById(Long id);
    void createRental(RentalDTO rentalDTO, MultipartFile picture, User owner);
    void updateRental(Long id, RentalDTO rentalDTO, MultipartFile picture, User currentUser);
    // Add this method to be used by MessageService
    Rental getRentalEntityById(Long id);
}