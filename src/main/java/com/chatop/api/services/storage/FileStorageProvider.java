package com.chatop.api.services.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageProvider {
    String  store(MultipartFile file);
    byte[]  retrieve(String fileName);
    void    delete(String fileName);
    boolean supports(String fileType);
}