package com.chatop.api.services.operations;

public interface RentalOperation<T, R> {
    R execute(T input);
}