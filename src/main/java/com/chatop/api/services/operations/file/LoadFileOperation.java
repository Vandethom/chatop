package com.chatop.api.services.operations.file;

import com.chatop.api.exceptions.FileStorageException;
import com.chatop.api.services.operations.FileOperation;
import com.chatop.api.services.storage.FileStorageProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadFileOperation implements FileOperation<String, byte[]> {

    private final List<FileStorageProvider> storageProviders;
    
    public LoadFileOperation(List<FileStorageProvider> storageProviders) {
        this.storageProviders = storageProviders;
    }

    @Override
    public byte[] execute(String fileName) {
        FileStorageException lastException = null;
        
        for (FileStorageProvider provider : storageProviders) {
            try {
                return provider.retrieve(fileName);
            } catch (FileStorageException e) {
                lastException = e;
                // Continue with next provider
            }
        }
        
        throw new FileStorageException("File not found: " + fileName, lastException);
    }
}