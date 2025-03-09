package com.chatop.api.mappers;

import com.chatop.api.dto.request.MessageDTO;
import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.request.UserDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.dto.response.UserResponseDTO;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public User toUser(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // TODO: needs encoding
        
        return user;
    }
    
    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreated_at(user.getCreatedAt().toLocalDateTime().format(formatter));
        dto.setUpdated_at(user.getUpdatedAt().toLocalDateTime().format(formatter));
        return dto;
    }
    
    public Rental toRental(RentalDTO dto) {
        Rental rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        
        return rental;
    }
    
    public RentalResponseDTO toRentalResponseDTO(Rental rental) {
        RentalResponseDTO dto = new RentalResponseDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwner_id(rental.getOwner().getId());
        dto.setCreated_at(rental.getCreatedAt().toLocalDateTime().format(formatter));
        dto.setUpdated_at(rental.getUpdatedAt().toLocalDateTime().format(formatter));
        return dto;
    }
    
    public List<RentalResponseDTO> toRentalResponseDTOList(List<Rental> rentals) {
        return rentals.stream()
                .map(this::toRentalResponseDTO)
                .collect(Collectors.toList());
    }
    
    public Message toMessage(MessageDTO dto, User user, Rental rental) {
        Message message = new Message();
        message.setMessage(dto.getMessage());
        message.setUser(user);
        message.setRental(rental);
        
        return message;
    }
}