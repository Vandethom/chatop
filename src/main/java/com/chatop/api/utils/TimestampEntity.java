package com.chatop.api.utils;

import java.sql.Timestamp;

public interface TimestampEntity {
    void setCreatedAt(Timestamp timestamp);
    void setUpdatedAt(Timestamp timestamp);
}