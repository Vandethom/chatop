package com.chatop.api.services.operations;

public interface AuthOperation<T, R> {
    R execute(T input);
}