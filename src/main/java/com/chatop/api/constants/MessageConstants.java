package com.chatop.api.constants;

public class MessageConstants {
    // Success messages
    public static final String RENTAL_CREATED  = "Rental created successfully";
    public static final String RENTAL_UPDATED  = "Rental updated successfully";
    public static final String MESSAGE_CREATED = "Message sent successfully";
    public static final String USER_REGISTERED = "User registered successfully";
    
    // Error messages
    public static final String RENTAL_NOT_FOUND           = "Rental not found with id: ";
    public static final String USER_NOT_FOUND             = "User not found with id: ";
    public static final String USER_EMAIL_NOT_FOUND       = "User not found with email: ";
    public static final String MESSAGE_NOT_FOUND          = "Message not found with id: ";
    public static final String AUTHENTICATION_REQUIRED    = "Authentication required";
    public static final String UNAUTHORIZED_RENTAL_UPDATE = "You are not authorized to update this rental";
    public static final String FILE_UPLOAD_ERROR          = "Failed to upload file";
    public static final String UNAUTHORIZED               = "Unauthorized access";
    public static final String INVALID_CREDENTIALS        = "Invalid email or password";
    public static final String EMAIL_ALREADY_EXISTS       = "Email already in use";
    public static final String JWT_EXPIRED                = "JWT token has expired";
    public static final String JWT_INVALID                = "Invalid JWT token";
    public static final String JWT_MISSING                = "JWT token is missing";

    private MessageConstants() {
        // Private constructor to prevent instantiation
    }
}