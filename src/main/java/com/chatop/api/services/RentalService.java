package com.chatop.api.services;

import com.chatop.api.constants.MessageConstants;
import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.request.RentalUpdateDTO;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.exceptions.UnauthorizedException;
import com.chatop.api.mappers.interfaces.IRentalMapper;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.services.interfaces.IFileStorageService;
import com.chatop.api.services.interfaces.IRentalService;
import com.chatop.api.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RentalService implements IRentalService {

    private final RentalRepository      rentalRepository;
    private final IRentalMapper         mapper;
    private final IFileStorageService   fileStorageService;
    private final TimeUtils             timeUtils;
    private final AuthenticationService authService;

    @Autowired
    public RentalService(
            RentalRepository      rentalRepository,
            IRentalMapper         mapper,
            IFileStorageService   fileStorageService,
            TimeUtils             timeUtils,
            AuthenticationService authService
            ) {
                this.rentalRepository   = rentalRepository;
                this.mapper             = mapper;
                this.fileStorageService = fileStorageService;
                this.timeUtils          = timeUtils;
                this.authService        = authService;
    }

    @Override
    public List<RentalResponseDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return mapper.toResponseDTOList(rentals);
    }

    @Override
    public RentalResponseDTO getRentalById(Long id) {
        Rental rental = getRentalEntityById(id);
        return mapper.toResponseDTO(rental);
    }

    @Override
    public Rental getRentalEntityById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));
    }

    @Override
    public void createRental(RentalDTO rentalDTO, MultipartFile picture, User owner) {
        Rental rental = mapper.toEntity(rentalDTO);
        rental.setOwner(owner);
        
        // Handle file upload if provided
        if (picture != null && !picture.isEmpty()) {
            String picturePath = fileStorageService.storeFile(picture);
            rental.setPicture(picturePath);
        }
        
        timeUtils.initializeTimestamps(rental);
        rentalRepository.save(rental);
    }

    @Override
    public void updateRental(Long id, RentalUpdateDTO rentalDTO, MultipartFile picture) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RENTAL_NOT_FOUND + id));
        User currentUser = authService.getCurrentUser();

        if (currentUser == null) {
            throw new UnauthorizedException(MessageConstants.AUTHENTICATION_REQUIRED);
        }

        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException(MessageConstants.UNAUTHORIZED_RENTAL_UPDATE);
        }

        if (rentalDTO.getName() != null) {
            rental.setName(rentalDTO.getName());
        }
        if (rentalDTO.getSurface() != null) {
            rental.setSurface(rentalDTO.getSurface());
        }
        if (rentalDTO.getPrice() != null) {
            rental.setPrice(rentalDTO.getPrice());
        }
        if (rentalDTO.getDescription() != null) {
            rental.setDescription(rentalDTO.getDescription());
        }
        if (picture != null && !picture.isEmpty()) {
            String picturePath = fileStorageService.storeFile(picture);
            rental.setPicture(picturePath);
        }

        timeUtils.updateTimestamp(rental);
        rentalRepository.save(rental);
    }
}