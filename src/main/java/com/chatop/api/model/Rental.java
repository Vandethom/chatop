package com.chatop.api.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "RENTAL")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name     = "name", 
        nullable = false
        )
    private String name;

    @Column(
        name     = "surface", 
        nullable = false
        )
    private Double surface;

    @Column(
        name     = "price", 
        nullable = false
        )
    private Double price;

    @Column(name = "picture")
    private String picture;

    @Column(
        name   = "description", 
        length = 2000
        )
    private String description;

    @ManyToOne
    @JoinColumn(
        name     = "owner_id", 
        nullable = false
        )
    private User owner;

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

    public Rental() {}

    public Rental(
        String name, 
        Double surface, 
        Double price, 
        String picture, 
        String description, 
        User owner, 
        Timestamp createdAt, 
        Timestamp updatedAt
        ) {
            this.name = name;
            this.surface = surface;
            this.price = price;
            this.picture = picture;
            this.description = description;
            this.owner = owner;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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