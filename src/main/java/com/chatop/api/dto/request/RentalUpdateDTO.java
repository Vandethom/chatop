package com.chatop.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rental update request")
public class RentalUpdateDTO {
    
    @Schema(description = "Name of the rental property", example = "Cozy Studio in Paris")
    private String name;
    
    @Schema(description = "Surface area in square meters", example = "45.5")
    private Double surface;
    
    @Schema(description = "Monthly rental price", example = "1200.0")
    private Double price;
    
    @Schema(description = "Detailed description of the property")
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