package com.chatop.api.exceptions;

import com.chatop.api.exceptions.handlers.ExceptionHandlerStrategy;
import com.chatop.api.factories.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ResponseFactory responseFactory;
    private final List<ExceptionHandlerStrategy<? extends Exception>> exceptionHandlers;

    public GlobalExceptionHandler(
        ResponseFactory                                     responseFactory, 
        List<ExceptionHandlerStrategy<? extends Exception>> exceptionHandlers
        ) {
            this.responseFactory   = responseFactory;
            this.exceptionHandlers = exceptionHandlers;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        for (ExceptionHandlerStrategy<? extends Exception> handler : exceptionHandlers) {
            if (handler.canHandle(ex)) {
                // Safe to cast because canHandle() validates the exception type
                @SuppressWarnings("unchecked")
                ResponseEntity<?> response = ((ExceptionHandlerStrategy<Exception>) handler).handle(ex, request);
                return response;
            }
        }
        
        // Default handling for unhandled exceptions
        logger.error("Unhandled exception", ex);
        return responseFactory.error(ex.getMessage());
    }
}