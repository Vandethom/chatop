package com.chatop.api.controller;

import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private static final String UPLOAD_DIR = "uploads/";
    
    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        List<Rental>        rentals  = rentalRepository.findAll();
        Map<String, Object> response = new HashMap<>();

        response.put("rentals", rentals);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isPresent()) {
            return ResponseEntity.ok(rental.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found");
        }
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRental(
            @RequestParam("name")        String name,
            @RequestParam("surface")     Double surface,
            @RequestParam("price")       Double price,
            @RequestParam("description") String description,
            @RequestParam(
                value    = "picture", 
                required = false
                ) MultipartFile picture) {
    
                try {
                    // Get current authenticated user
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    UserDetails    userDetails    = (UserDetails) authentication.getPrincipal();
                    User           owner          = userRepository.findByEmail(userDetails.getUsername())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    
                    Rental rental = new Rental();
                    rental.setName(name);
                    rental.setSurface(surface);
                    rental.setPrice(price);
                    rental.setDescription(description);
                    rental.setOwner(owner);
                    
                    // Handle file upload if provided
                    if (
                        picture != null 
                    && !picture.isEmpty()
                    ) {
                        // Create uploads directory if it doesn't exist
                        File directory = new File(UPLOAD_DIR);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }
                        
                        // Generate unique filename to avoid collisions
                        String originalFilename = picture.getOriginalFilename();
                        String fileExtension    = originalFilename.substring(originalFilename.lastIndexOf('.'));
                        String newFilename      = UUID.randomUUID().toString() + fileExtension;
                        
                        // Save file to disk
                        Path filePath = Paths.get(UPLOAD_DIR + newFilename);
                        Files.write(filePath, picture.getBytes());
                        
                        // Set the picture URL in the rental
                        rental.setPicture("/uploads/" + newFilename);
                    }
                    
                    Timestamp now = new Timestamp(System.currentTimeMillis());

                    rental.setCreatedAt(now);
                    rental.setUpdatedAt(now);
                    rentalRepository.save(rental);
                    
                    // Return success response
                    Map<String, String> response = new HashMap<>();
                    response.put(
                        "message", 
                        "Rental created successfully"
                        );
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                    
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to upload picture: " + e.getMessage());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error creating rental: " + e.getMessage());
                }
            }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description) {
        
        try {
            Optional<Rental> existingRental = rentalRepository.findById(id);
            
            if (existingRental.isPresent()) {
                Rental rental = existingRental.get();
                
                rental.setName(name);
                rental.setSurface(surface);
                rental.setPrice(price);
                rental.setDescription(description);
                rental.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                
                rentalRepository.save(rental);
                
                Map<String, String> response = new HashMap<>();
                response.put(
                    "message", 
                    "Rental updated successfully"
                    );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating rental: " + e.getMessage());
        }
    }
}