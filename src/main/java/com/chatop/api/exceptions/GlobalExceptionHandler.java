package com.chatop.api.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chatop.api.constants.MessageConstants;
import com.chatop.api.dto.response.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String getPath(WebRequest request) {
        return ((HttpServletRequest) request).getRequestURI();
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex, 
            WebRequest                request
            ) {
                return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
            }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileStorageException(
            FileStorageException ex, 
            WebRequest           request
            ) {
                return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, null);
            }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbiddenException(
            ForbiddenException ex, 
            WebRequest         request
            ) {
                return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request, null);
            }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(
            UnauthorizedException ex, 
            WebRequest            request
            ) {
                return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request, null);
            }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, 
            WebRequest                 request
            ) {
                return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request, null);
            }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(
            BadCredentialsException ex, 
            WebRequest              request
            ) {
                return buildErrorResponse(HttpStatus.UNAUTHORIZED, MessageConstants.INVALID_CREDENTIALS, request, null);
            }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(
            AuthenticationException ex, 
            WebRequest              request
            ) {
                return buildErrorResponse(HttpStatus.UNAUTHORIZED, MessageConstants.AUTHENTICATION_REQUIRED, request, null);
            }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(
            AccessDeniedException ex, 
            WebRequest            request
            ) {
                return buildErrorResponse(HttpStatus.FORBIDDEN, MessageConstants.ACCESS_DENIED, request, null);
            }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(
            Exception ex, 
            WebRequest request) {
            
        if (ex instanceof MaxUploadSizeExceededException) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, MessageConstants.FILE_SIZE_EXCEEDED, request, null);
        }
        
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNEXPECTED_ERROR, request, null);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders                     headers,
            @NonNull HttpStatusCode                  status,
            @NonNull WebRequest                      request) {
        
        System.out.println("Custom handleMethodArgumentNotValid called!");
        
        Map<String, String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage(),
                        (first, second) -> first + "; " + second
                ));
        
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST,
                MessageConstants.VALIDATION_FAILED,
                getPath(request),
                validationErrors
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus status, 
            String     message, 
            WebRequest request, 
            Object     details
            ) {
                ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                        status, 
                        message, 
                        getPath(request), 
                        details
                );

                return new ResponseEntity<>(errorResponse, status);
            }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            ValidationException ex, 
            WebRequest          request
            ) {
                return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, ex.getErrors());
            }
}