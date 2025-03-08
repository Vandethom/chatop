package com.chatop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}