package com.chatop.api.controllers;

import com.chatop.api.constants.MessageConstants;
import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.request.RentalUpdateDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.RentalsResponseDTO;
import com.chatop.api.interfaces.IRentalService;
import com.chatop.api.models.User;

import com.chatop.api.services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "Endpoints for managing rental properties")
public class RentalController {

    private final IRentalService        rentalService;
    private final AuthenticationService authService;

    @Autowired
    public RentalController(
        IRentalService        rentalService, 
        AuthenticationService authService
        ) {
            this.rentalService   = rentalService;
            this.authService     = authService;
    }

    @Operation(summary = "Get all rentals", description = "Returns a list of all available rental properties")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of rentals returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalsResponseDTO.class))), @ApiResponse(responseCode = "401", description = "Not authenticated") })
    @GetMapping("")
    public ResponseEntity<Map<String, List<RentalResponseDTO>>> getAllRentals() {
        List<RentalResponseDTO>              rentals  = rentalService.getAllRentals();
        Map<String, List<RentalResponseDTO>> response = new HashMap<>();

        response.put("rentals", rentals);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get rental by ID", description = "Returns details of a specific rental property")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Rental found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDTO.class))), @ApiResponse(responseCode = "404", description = "Rental not found"), @ApiResponse(responseCode = "401", description = "Not authenticated")})
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(
    @Parameter(description = "Rental ID") 
    @PathVariable Long id) {
        RentalResponseDTO rental = rentalService.getRentalById(id);

        return ResponseEntity.ok(rental);
    }

    @Operation(summary = "Create a new rental", description = "Creates a new rental property with the logged in user as owner")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Rental created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid input"), @ApiResponse(responseCode = "401", description = "Not authenticated") })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDTO> createRental(
            @Valid @ModelAttribute RentalDTO rentalDTO,
            @RequestParam("picture") MultipartFile picture
            ) {
        
                User currentUser = authService.getCurrentUser();

                rentalService.createRental(rentalDTO, picture, currentUser);
        
                MessageResponseDTO response = new MessageResponseDTO();

                response.setMessage(MessageConstants.RENTAL_CREATED);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

    @Operation(summary = "Update a rental", description = "Updates an existing rental property. Only the owner can update their rental.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Rental updated successfully",content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid input"), @ApiResponse(responseCode = "401", description = "Not authenticated"), @ApiResponse(responseCode = "403", description = "Not authorized to update this rental"), @ApiResponse(responseCode = "404", description = "Rental not found") })
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateRental(
            @Parameter(description = "Rental ID") @PathVariable Long id,
            @Valid @RequestBody RentalUpdateDTO rentalUpdateDTO,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {
        
        rentalService.updateRental(id, rentalUpdateDTO, picture);
        
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(MessageConstants.RENTAL_UPDATED);
        return ResponseEntity.ok(response);
    }
}
