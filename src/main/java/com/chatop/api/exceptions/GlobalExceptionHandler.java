package com.chatop.api.exceptions;

import com.chatop.api.constants.MessageConstants;
import com.chatop.api.dto.response.MessageResponseDTO;
import com.chatop.api.factories.ResponseFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger   logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ResponseFactory responseFactory;

    @Autowired
    public GlobalExceptionHandler(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return responseFactory.notFound(ex.getMessage());
    }
    
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<MessageResponseDTO> handleFileStorageException(FileStorageException ex) {
        logger.error("File storage error", ex);
        return responseFactory.error("Failed to upload file");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MessageResponseDTO> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        return responseFactory.forbidden(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        return responseFactory.unauthorized(MessageConstants.INVALID_CREDENTIALS);
    }

    //@ExceptionHandler(AccessDeniedException.class)
    //public ResponseEntity<MessageResponseDTO> handleAccessDeniedException(AccessDeniedException ex) {
    //    return responseFactory.forbidden(MessageConstants.UNAUTHORIZED);
    //}

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation", ex);
        
        if (ex.getMessage().contains("email") && ex.getMessage().contains("unique")) {
            return responseFactory.badRequest(MessageConstants.EMAIL_ALREADY_EXISTS);
        }
        
        return responseFactory.badRequest("Data integrity violation: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status",    HttpStatus.FORBIDDEN.value());
        body.put("error",     "Forbidden");
        body.put("message",   "Authentication required to access this resource");
        body.put("path",      ((ServletWebRequest) request).getRequest().getRequestURI());
        
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status",    HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error",     "Internal Server Error");
        body.put("message",   ex.getMessage());
        body.put("path",      ((ServletWebRequest) request).getRequest().getRequestURI());
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}