package com.chatop.api.utils;

import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
public class TimeUtils {
    
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    public void initializeTimestamps(TimestampEntity entity) {
        Timestamp now = getCurrentTimestamp();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }
    
    public void updateTimestamp(TimestampEntity entity) {
        entity.setUpdatedAt(getCurrentTimestamp());
    }
}