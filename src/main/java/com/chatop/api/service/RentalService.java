package com.chatop.api.service;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.exception.ResourceNotFoundException;
import com.chatop.api.mapper.EntityMapper;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository   rentalRepository;
    private final EntityMapper       mapper;
    private final FileStorageService fileStorageService;
    private final TimeUtils          timeUtils;

    @Autowired
    public RentalService(
            RentalRepository   rentalRepository,
            EntityMapper       mapper,
            FileStorageService fileStorageService,
            TimeUtils          timeUtils) {
        this.rentalRepository   = rentalRepository;
        this.mapper             = mapper;
        this.fileStorageService = fileStorageService;
        this.timeUtils          = timeUtils;
    }

    public List<RentalResponseDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();

        return mapper.toRentalResponseDTOList(rentals);
    }

    public RentalResponseDTO getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));

        return mapper.toRentalResponseDTO(rental);
    }

    public void createRental(RentalDTO rentalDTO, MultipartFile picture, User owner) {
        Rental rental = mapper.toRental(rentalDTO);
        rental.setOwner(owner);
        
        // Handle file upload if provided
        if (picture != null && !picture.isEmpty()) {
            String picturePath = fileStorageService.storeFile(picture);
            rental.setPicture(picturePath);
        }
        
        timeUtils.initializeTimestamps(rental);
        rentalRepository.save(rental);
    }

    public void updateRental(Long id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));
        
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        
        timeUtils.updateTimestamp(rental);
        rentalRepository.save(rental);
    }
}
