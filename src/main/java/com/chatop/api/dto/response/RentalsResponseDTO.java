package com.chatop.api.dto.response;

import java.util.List;

public class RentalsResponseDTO {
    private List<RentalResponseDTO> rentals;

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