package com.chatop.api.exceptions.handlers;

import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.factories.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class ResourceNotFoundExceptionHandler implements ExceptionHandlerStrategy<ResourceNotFoundException> {
    
    private final ResponseFactory responseFactory;
    
    public ResourceNotFoundExceptionHandler(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }
    
    @Override
    public boolean canHandle(Exception exception) {
        return exception instanceof ResourceNotFoundException;
    }
    
    @Override
    public ResponseEntity<?> handle(ResourceNotFoundException exception, WebRequest request) {
        return responseFactory.notFound(exception.getMessage());
    }
}