package com.chatop.api.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String storeFile(MultipartFile file);
    byte[] loadFileAsBytes(String fileName);
}