package com.chatop.api.services.operations;

public interface UserOperation<T, R> {
    R execute(T input);
}