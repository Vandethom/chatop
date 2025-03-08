package com.chatop.api.services;

import com.chatop.api.exceptions.FileStorageException;
import com.chatop.api.services.interfaces.IFileStorageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService implements IFileStorageService {
    
    private final Path fileStorageLocation;
    
    @Value("${app.base-url:http://localhost:3001}")
    private String baseUrl;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("Upload directory initialized: " + this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            // Normalize file name
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            
            if (originalFileName != null) {
                int dotIndex = originalFileName.lastIndexOf('.');
                if (dotIndex > 0) {
                    fileExtension = originalFileName.substring(dotIndex);
                }
            } else {
                originalFileName = "file.bin";
                fileExtension = ".bin";
            }
            
            // Generate unique filename
            String fileName = UUID.randomUUID() + fileExtension;

            // Copy file to the target location
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Return absolute URL path
            return baseUrl + "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store file. Please try again!", ex);
        }
    }
}