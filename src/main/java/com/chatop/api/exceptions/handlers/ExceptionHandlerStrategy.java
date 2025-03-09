package com.chatop.api.exceptions.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface ExceptionHandlerStrategy<T extends Exception> {
    boolean           canHandle(Exception exception);
    ResponseEntity<?> handle(T exception, WebRequest request);
}