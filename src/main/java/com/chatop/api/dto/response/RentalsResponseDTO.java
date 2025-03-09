package com.chatop.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Collection of rental properties")
public class RentalsResponseDTO {
    private List<RentalResponseDTO> rentals;
    
    public RentalsResponseDTO() {
    }
    
    public RentalsResponseDTO(List<RentalResponseDTO> rentals) {
        this.rentals = rentals;
    }
    
    public List<RentalResponseDTO> getRentals() {
        return rentals;
    }
    
    public void setRentals(List<RentalResponseDTO> rentals) {
        this.rentals = rentals;
    }
}