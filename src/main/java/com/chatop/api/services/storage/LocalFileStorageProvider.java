package com.chatop.api.services.storage;

import com.chatop.api.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Component
public class LocalFileStorageProvider implements FileStorageProvider {

    private final Path fileStorageLocation;
    
    private static final Set<String> SUPPORTED_IMAGE_TYPES = Set.of(
        "image/jpeg",
        "image/jpg",
        "image/png", 
        "image/gif",
        "image/bmp"
    );
    
    @Override
    public Set<String> getSupportedContentTypes() {
        return SUPPORTED_IMAGE_TYPES;
    }

    @Override
    public boolean supports(String contentType) {
        return contentType != null
                           && SUPPORTED_IMAGE_TYPES.contains(
                            contentType.toLowerCase()
                            );
    }
    
    public LocalFileStorageProvider(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String store(MultipartFile file) {
        if (file == null) {
            throw new FileStorageException("Cannot store null file");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            originalFileName = "unknown_file";
        } else {
            originalFileName = StringUtils.cleanPath(originalFileName);
        }
        
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public byte[] retrieve(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new FileStorageException("Could not delete file " + fileName, ex);
        }
    }
}