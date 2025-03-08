package com.chatop.api.utils;

import java.sql.Timestamp;

public interface TimestampAware {    
    void      setCreatedAt(Timestamp timestamp);
    void      setUpdatedAt(Timestamp timestamp);
    Timestamp getCreatedAt();
    Timestamp getUpdatedAt();
    
    default void initializeTimestamps(Timestamp now) {
        setCreatedAt(now);
        setUpdatedAt(now);
    }
    
    default void updateTimestamp(Timestamp now) {
        setUpdatedAt(now);
    }
}