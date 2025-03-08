package com.chatop.api.service;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.exception.ResourceNotFoundException;
import com.chatop.api.mapper.interfaces.IRentalMapper;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.service.interfaces.IFileStorageService;
import com.chatop.api.service.interfaces.IRentalService;
import com.chatop.api.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RentalService implements IRentalService {

    private final RentalRepository    rentalRepository;
    private final IRentalMapper       mapper;
    private final IFileStorageService fileStorageService;
    private final TimeUtils           timeUtils;

    @Autowired
    public RentalService(
            RentalRepository    rentalRepository,
            IRentalMapper       mapper,
            IFileStorageService fileStorageService,
            TimeUtils           timeUtils
            ) {
                this.rentalRepository   = rentalRepository;
                this.mapper             = mapper;
                this.fileStorageService = fileStorageService;
                this.timeUtils          = timeUtils;
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
    public void updateRental(Long id, RentalDTO rentalDTO) {
        Rental rental = getRentalEntityById(id);
        
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        
        timeUtils.updateTimestamp(rental);
        rentalRepository.save(rental);
    }
}