package com.chatop.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Rental creation/update request")
public class RentalDTO {
    
    @Schema(description = "Name of the rental property", example = "Cozy Studio in Paris")
    @NotBlank(message = "{NotBlank.rentalDTO.name}")
    private String name;
    
    @Schema(description = "Surface area in square meters", example = "45.5")
    @NotNull(message = "{NotNull.rentalDTO.surface}")
    @Positive(message = "{Positive.rentalDTO.surface}")
    private Double surface;
    
    @Schema(description = "Monthly rental price", example = "1200.0")
    @NotNull(message = "{NotNull.rentalDTO.price}")
    @Positive(message = "{Positive.rentalDTO.price}")
    private Double price;
    
    @Schema(description = "Detailed description of the property", 
            example = "Beautiful studio apartment with a view of the Eiffel Tower")
    @NotBlank(message = "{NotBlank.rentalDTO.description}")
    private String description;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}