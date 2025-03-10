package com.chatop.api.services.operations;

public interface Operation<T, R> {
    R execute(T input);
}