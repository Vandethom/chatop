package com.chatop.api.services.operations;

public interface MessageOperation<T, R> {
    R execute(T input);
}