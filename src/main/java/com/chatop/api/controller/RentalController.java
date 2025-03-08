package com.chatop.api.controller;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.RentalsResponseDTO;
import com.chatop.api.model.User;

import com.chatop.api.service.interfaces.IRentalService;
import com.chatop.api.service.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final IRentalService rentalService;
    private final IUserService userService;

    @Autowired
    public RentalController(IRentalService rentalService, IUserService userService) {
        this.rentalService = rentalService;
        this.userService   = userService;
    }

    @GetMapping
    public ResponseEntity<RentalsResponseDTO> getAllRentals() {
        List<RentalResponseDTO> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(new RentalsResponseDTO(rentals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {
    
        try {
            // Create a RentalDTO manually from form parts
            RentalDTO rentalDTO = new RentalDTO();
            rentalDTO.setName(name);
            rentalDTO.setSurface(surface);
            rentalDTO.setPrice(price);
            rentalDTO.setDescription(description);
    
            // Get current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User owner = userService.findByEmail(userDetails.getUsername());
    
            rentalService.createRental(rentalDTO, picture, owner);
    
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponseDTO("Rental created successfully"));
        } catch (Exception e) {
            System.err.println("Error creating rental: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDTO("Error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateRental(
            @PathVariable Long id,
            @Valid @RequestBody RentalDTO rentalDTO) {
        
        rentalService.updateRental(id, rentalDTO);
        return ResponseEntity.ok(new MessageResponseDTO("Rental updated successfully"));
    }
}