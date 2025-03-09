package com.chatop.api.services.operations;

public interface FileOperation<T, R> {
    R execute(T input);
}