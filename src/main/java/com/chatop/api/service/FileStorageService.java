package com.chatop.api.service;

import com.chatop.api.exception.FileStorageException;
import com.chatop.api.service.interfaces.IFileStorageService;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService implements IFileStorageService {
    
    private static final String UPLOAD_DIR = "uploads/";
    
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            System.out.println("Upload directory initialized: " + UPLOAD_DIR);
        } catch (IOException e) {
            System.err.println("Could not create upload directory: " + e.getMessage());
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "file.bin";
            }
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFilename   = UUID.randomUUID().toString() + fileExtension;
            
            Path filePath = Paths.get(UPLOAD_DIR + newFilename);

            Files.write(filePath, file.getBytes());
            
            return "/uploads/" + newFilename;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + e.getMessage(), e);
        }
    }
}