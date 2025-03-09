package com.chatop.api.factories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder<T> {
    private HttpStatus status    = HttpStatus.OK;
    private Date       timestamp = new Date();
    private String     message;
    private T          data;
    private String     path;
    private String     error;

    public ResponseBuilder<T> withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseBuilder<T> withData(T data) {
        this.data = data;
        return this;
    }

    public ResponseBuilder<T> withPath(String path) {
        this.path = path;
        return this;
    }
    
    public ResponseBuilder<T> withError(String error) {
        this.error = error;
        return this;
    }

    public ResponseEntity<Object> build() {
        Map<String, Object> body = new HashMap<>();
        
        body.put("timestamp", timestamp);
        body.put("status",    status.value());

        if (message != null) {
            body.put("message", message);
        }

        if (data != null) {
            body.put("data", data);
        }
        
        if (error != null) {
            body.put("error", error);
        }

        if (path != null) {
            body.put("path", path);
        }

        return new ResponseEntity<>(body, status);
    }
}