package com.chatop.api.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(
        name     = "message", 
        length   = 2000, 
        nullable = false
        )
    private String message;

    @Column(
        name     = "created_at", 
        nullable = false
        )
    private Timestamp createdAt;

    @Column(
        name     = "updated_at", 
        nullable = false
        )
    private Timestamp updatedAt;

    public Message() {}

    public Message(
        Rental rental, 
        User user, 
        String message, 
        Timestamp createdAt, 
        Timestamp updatedAt
        ) {
            this.rental = rental;
            this.user = user;
            this.message = message;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}