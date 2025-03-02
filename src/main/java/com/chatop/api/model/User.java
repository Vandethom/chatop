package com.chatop.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @OneToMany(mappedBy = "owner")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;
    
    @Column(
        name     = "email", 
        nullable = false, 
        unique   = true
        )
    private String email;

    @Column(
        name     = "name", 
        nullable = false
        )
    private String name;

    @Column(
        name     = "password", 
        nullable = false
        )
    private String password;

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

    public User() {}

    public User(
        String    email, 
        String    name, 
        String    password, 
        Timestamp createdAt, 
        Timestamp updatedAt
        ) {
            this.email     = email;
            this.name      = name;
            this.password  = password;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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