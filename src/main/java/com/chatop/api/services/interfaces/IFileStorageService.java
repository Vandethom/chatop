package com.chatop.api.services.interfaces;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    Set<String> getSupportedContentTypes();
    default boolean supports(String contentType) {
        return contentType != null 
                           && getSupportedContentTypes().contains(
                            contentType.toLowerCase()
                            );
    }
    String storeFile(MultipartFile file);
    byte[] retrieve(String fileName);
}