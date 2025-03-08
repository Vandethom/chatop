package com.chatop.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rental details response")
public class RentalResponseDTO {
    
    @Schema(description = "Unique identifier", example = "1")
    private Long id;
    
    @Schema(description = "Name of the rental", example = "Cozy Studio in Paris")
    private String name;
    
    @Schema(description = "Surface area in square meters", example = "45.5")
    private Double surface;
    
    @Schema(description = "Monthly rental price", example = "1200.0")
    private Double price;
    
    @Schema(description = "URL to the rental image", example = "http://localhost:3001/uploads/image.jpg")
    private String picture;
    
    @Schema(description = "Detailed description of the property")
    private String description;
    
    @Schema(description = "ID of the owner", example = "3")
    private Long owner_id;
    
    @Schema(description = "Creation date in ISO format", example = "2025-03-08T12:30:45.000Z")
    private String created_at;
    
    @Schema(description = "Last update date in ISO format", example = "2025-03-08T14:15:22.000Z")
    private String updated_at;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}