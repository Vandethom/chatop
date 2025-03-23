package com.chatop.api.dto.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
    private String        message;
    private int           status;
    private String        error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private String        path;
    private Object        details;

    public ErrorResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponseDTO(
        HttpStatus httpStatus, 
        String     message, 
        String     path
        ) {
            this();
            this.status  = httpStatus.value();
            this.error   = httpStatus.getReasonPhrase();
            this.message = message;
            this.path    = path;
        }

    public ErrorResponseDTO(
        HttpStatus httpStatus, 
        String     message, 
        String     path, 
        Object     details
        ) {
            this(httpStatus, message, path);
            this.details = details;
        }

    public String getMessage() { 
        return message; 
    
    }
    public void setMessage(String message) { 
        this.message = message; 
    }
    
    public int getStatus() { 
        return status; 
    }
    public void setStatus(int status) { 
        this.status = status; 
    }
    
    public String getError() { 
        return error; 
    }
    public void setError(String error) { 
        this.error = error; 
    }
    
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }
    public void setTimestamp(LocalDateTime timestamp) { 
        this.timestamp = timestamp; 
    }
    
    public String getPath() { 
        return path; 
    }

    public void setPath(String path) { 
        this.path = path; 
    }
    
    public Object getDetails() { 
        return details; 
    }

    public void setDetails(Object details) { 
        this.details = details; 
    }

    @Override
    public String toString() {
        return "ErrorResponseDTO{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                ", path='" + path + '\'' +
                ", details=" + details +
                '}';
    }
}