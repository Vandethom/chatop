package com.chatop.api.services;

import com.chatop.api.services.interfaces.IFileStorageService;
import com.chatop.api.services.operations.file.LoadFileOperation;
import com.chatop.api.services.operations.file.StoreFileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements IFileStorageService {

    private final StoreFileOperation storeFileOperation;
    private final LoadFileOperation  loadFileOperation;
    
    @Autowired
    public FileStorageService(
            StoreFileOperation storeFileOperation,
            LoadFileOperation loadFileOperation) {
        this.storeFileOperation = storeFileOperation;
        this.loadFileOperation  = loadFileOperation;
    }

    @Override
    public String storeFile(MultipartFile file) {
        return storeFileOperation.execute(file);
    }

    @Override
    public byte[] loadFileAsBytes(String fileName) {
        return loadFileOperation.execute(fileName);
    }
}