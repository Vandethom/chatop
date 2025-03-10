package com.chatop.api.services.operations.file;

import com.chatop.api.exceptions.FileStorageException;
import com.chatop.api.services.operations.FileOperation;
import com.chatop.api.services.storage.FileStorageProvider;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class StoreFileOperation implements FileOperation<MultipartFile, String> {

    private final List<FileStorageProvider> storageProviders;
    
    public StoreFileOperation(List<FileStorageProvider> storageProviders) {
        this.storageProviders = storageProviders;
    }

    @Override
    public String execute(MultipartFile file) {
        String contentType = file.getContentType();
        
        for (FileStorageProvider provider : storageProviders) {
            if (provider.supports(contentType)) {
                return provider.store(file);
            }
        }
        
        throw new FileStorageException("No storage provider found for file type: " + contentType);
    }

        public Set<String> getSupportedContentTypes() {
        Set<String> allSupportedTypes = new HashSet<>();
        
        for (FileStorageProvider provider : storageProviders) {
            allSupportedTypes.addAll(provider.getSupportedContentTypes());
        }
        
        return allSupportedTypes;
    }
}