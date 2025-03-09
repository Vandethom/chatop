package com.chatop.api.services;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.request.RentalUpdateDTO;
import com.chatop.api.dto.response.RentalResponseDTO;

import com.chatop.api.exceptions.ResourceNotFoundException;

import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import com.chatop.api.repositories.RentalRepository;

import com.chatop.api.services.interfaces.IRentalService;

import com.chatop.api.services.operations.rental.CreateRentalInput;
import com.chatop.api.services.operations.rental.CreateRentalOperation;
import com.chatop.api.services.operations.rental.GetAllRentalsOperation;
import com.chatop.api.services.operations.rental.GetRentalByIdOperation;
import com.chatop.api.services.operations.rental.UpdateRentalInput;
import com.chatop.api.services.operations.rental.UpdateRentalOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RentalService implements IRentalService {

    private final RentalRepository       rentalRepository;
    private final CreateRentalOperation  createRentalOperation;
    private final GetAllRentalsOperation getAllRentalsOperation;
    private final GetRentalByIdOperation getRentalByIdOperation;
    private final UpdateRentalOperation  updateRentalOperation;

    @Autowired
    public RentalService(
            RentalRepository      rentalRepository,
            CreateRentalOperation createRentalOperation,
            GetAllRentalsOperation getAllRentalsOperation,
            GetRentalByIdOperation getRentalByIdOperation,
            UpdateRentalOperation  updateRentalOperation) {
        this.rentalRepository       = rentalRepository;
        this.createRentalOperation  = createRentalOperation;
        this.getAllRentalsOperation = getAllRentalsOperation;
        this.getRentalByIdOperation = getRentalByIdOperation;
        this.updateRentalOperation  = updateRentalOperation;
    }

    @Override
    public List<RentalResponseDTO> getAllRentals() {
        return getAllRentalsOperation.execute(null);
    }

    @Override
    public RentalResponseDTO getRentalById(Long id) {
        return getRentalByIdOperation.execute(id);
    }

    @Override
    public Rental getRentalEntityById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental", "id", id));
    }

    @Override
    public void createRental(RentalDTO rentalDTO, MultipartFile picture, User owner) {
        CreateRentalInput input = new CreateRentalInput(rentalDTO, picture, owner);
        createRentalOperation.execute(input);
    }

    @Override
    public void updateRental(Long id, RentalUpdateDTO rentalDTO, MultipartFile picture) {
        UpdateRentalInput input = new UpdateRentalInput(id, rentalDTO, picture);
        updateRentalOperation.execute(input);
    }
}