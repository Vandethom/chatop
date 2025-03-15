package com.chatop.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.chatop.api.mappers.interfaces.IMessageMapper;
import com.chatop.api.mappers.interfaces.IRentalMapper;
import com.chatop.api.mappers.interfaces.IUserMapper;
import com.chatop.api.mappers.MessageMapperStruct;
import com.chatop.api.mappers.RentalMapperStruct;
import com.chatop.api.mappers.UserMapperStruct;

@Configuration
public class MapperConfig {

    @Bean
    @Primary
    public IMessageMapper messageMapper(MessageMapperStruct messageMapperStruct) {
        return new MapStructMessageMapperAdapter(messageMapperStruct);
    }

    @Bean
    @Primary
    public IRentalMapper rentalMapper(RentalMapperStruct rentalMapperStruct) {
        return new MapStructRentalMapperAdapter(rentalMapperStruct);
    }

    @Bean
    @Primary
    public IUserMapper userMapper(UserMapperStruct userMapperStruct) {
        return new MapStructUserMapperAdapter(userMapperStruct);
    }
}