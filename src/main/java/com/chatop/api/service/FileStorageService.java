package com.chatop.api.service;

import com.chatop.api.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    
    private static final String UPLOAD_DIR = "uploads/";
    
    public String storeFile(MultipartFile file) {
        try {
            // Create uploads directory if it doesn't exist
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Generate unique filename to avoid collisions
            String originalFilename = file.getOriginalFilename();
            String fileExtension    = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFilename      = UUID.randomUUID().toString() + fileExtension;
            Path filePath           = Paths.get(UPLOAD_DIR + newFilename);
            
            Files.write(filePath, file.getBytes());
            
            return "/uploads/" + newFilename;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + e.getMessage());
        }
    }
}