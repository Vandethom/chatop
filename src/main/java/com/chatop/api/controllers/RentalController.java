package com.chatop.api.controllers;

import com.chatop.api.constants.MessageConstants;
import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.request.RentalUpdateDTO;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.RentalsResponseDTO;

import com.chatop.api.factories.RentalDTOFactory;
import com.chatop.api.factories.ResponseFactory;

import com.chatop.api.models.User;

import com.chatop.api.services.AuthenticationService;
import com.chatop.api.services.interfaces.IRentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rentals", description = "Endpoints for managing rental properties")
public class RentalController {

    private final IRentalService        rentalService;
    private final AuthenticationService authService;
    private final RentalDTOFactory      dtoFactory;
    private final ResponseFactory       responseFactory;

    @Autowired
    public RentalController(
        IRentalService        rentalService, 
        AuthenticationService authService,
        RentalDTOFactory      dtoFactory,
        ResponseFactory       responseFactory
        ) {
            this.rentalService   = rentalService;
            this.authService     = authService;
            this.dtoFactory      = dtoFactory;
            this.responseFactory = responseFactory;
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
        
        return responseFactory.successRentals(rentals);
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
        RentalResponseDTO rental = rentalService.getRentalById(id);

        return responseFactory.successRental(rental);
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
        @Parameter(description = "Name of the rental")            @RequestParam("name")              String name,
        @Parameter(description = "Surface area in square meters") @RequestParam("surface")           Double surface,
        @Parameter(description = "Monthly price")                 @RequestParam("price")             Double price,
        @Parameter(description = "Detailed description")          @RequestParam("description")       String description,
        @Parameter(description = "Property image file")           @RequestParam(value    = "picture", 
                                                                                required = false)    MultipartFile picture
    ) {
        RentalDTO rentalDTO = dtoFactory.createRentalDTO(name, surface, price, description);
        User      owner     = authService.getCurrentUser();

        rentalService.createRental(rentalDTO, picture, owner);

        return responseFactory.created(MessageConstants.RENTAL_CREATED);
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
        RentalUpdateDTO rentalDTO = dtoFactory.createRentalUpdateDTO(name, surface, price, description);
            
        rentalService.updateRental(id, rentalDTO, picture);
            
        return responseFactory.success(MessageConstants.RENTAL_UPDATED);
    }
}