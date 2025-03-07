package com.chatop.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RentalDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Surface is required")
    @Positive(message = "Surface must be positive")
    private Double surface;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    @NotBlank(message = "Description is required")
    private String description;
    
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