package com.chatop.api.controllers;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.request.RentalUpdateDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.RentalsResponseDTO;
import com.chatop.api.models.User;
import com.chatop.api.services.interfaces.IRentalService;
import com.chatop.api.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Rentals", description = "Endpoints for managing rental properties")
public class RentalController {

    private final IRentalService rentalService;
    private final IUserService userService;

    @Autowired
    public RentalController(IRentalService rentalService, IUserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @Operation(
        summary = "Get all rentals", 
        description = "Returns a list of all available rental properties"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of rentals returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalsResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping
    public ResponseEntity<RentalsResponseDTO> getAllRentals() {
        List<RentalResponseDTO> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(new RentalsResponseDTO(rentals));
    }

    @Operation(
        summary = "Get rental by ID", 
        description = "Returns details of a specific rental property"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Rental not found"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(
        @Parameter(description = "ID of the rental to retrieve") @PathVariable Long id
    ) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @Operation(
        summary = "Create a new rental", 
        description = "Creates a new rental property with the logged in user as owner"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rental created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> createRental(
        @Parameter(description = "Name of the rental") @RequestParam("name") String name,
        @Parameter(description = "Surface area in square meters") @RequestParam("surface") Double surface,
        @Parameter(description = "Monthly price") @RequestParam("price") Double price,
        @Parameter(description = "Detailed description") @RequestParam("description") String description,
        @Parameter(description = "Property image file") @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
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

    @Operation(
        summary = "Update a rental", 
        description = "Updates an existing rental property. Only the owner can update their rental."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this rental"),
        @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> updateRental(
        @Parameter(description = "ID of the rental to update")    @PathVariable Long id,
        @Parameter(description = "Name of the rental")            @RequestParam(value = "name",        required = false) String        name,
        @Parameter(description = "Surface area in square meters") @RequestParam(value = "surface",     required = false) Double        surface,
        @Parameter(description = "Monthly price")                 @RequestParam(value = "price",       required = false) Double        price,
        @Parameter(description = "Detailed description")          @RequestParam(value = "description", required = false) String        description,
        @Parameter(description = "Property image file")           @RequestParam(value = "picture",     required = false) MultipartFile picture
    ) {
        try {
            RentalUpdateDTO rentalDTO = new RentalUpdateDTO();
            if (name        != null) rentalDTO.setName(name);
            if (surface     != null) rentalDTO.setSurface(surface);
            if (price       != null) rentalDTO.setPrice(price);
            if (description != null) rentalDTO.setDescription(description);
            
            // Get current authenticated user for security check
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByEmail(userDetails.getUsername());
            
            // Update the rental with possible new picture
            rentalService.updateRental(id, rentalDTO, picture, currentUser);
            
            return ResponseEntity.ok(new MessageResponseDTO("Rental updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDTO("Error: " + e.getMessage()));
        }
    }
}