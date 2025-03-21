package com.chatop.api.services.storage;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageProvider {
    Set<String> getSupportedContentTypes();
    String  store(MultipartFile file);
    byte[]  retrieve(String fileName);
    void    delete(String fileName);
    boolean supports(String fileType);
}