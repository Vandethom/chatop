package com.chatop.api.utils;

import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
public class TimeUtils {
    
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    public void initializeTimestamps(TimestampAware entity) {
        Timestamp now = getCurrentTimestamp();
        entity.initializeTimestamps(now);
    }
    
    public void updateTimestamp(TimestampAware entity) {
        entity.updateTimestamp(getCurrentTimestamp());
    }
}