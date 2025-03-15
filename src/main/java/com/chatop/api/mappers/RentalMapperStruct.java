package com.chatop.api.mappers;

import com.chatop.api.dto.request.RentalDTO;
import com.chatop.api.dto.response.RentalResponseDTO;
import com.chatop.api.models.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RentalMapperStruct {

    @Mapping(target = "id",        ignore     = true)
    @Mapping(target = "owner",     ignore     = true)
    @Mapping(target = "picture",   ignore     = true)
    @Mapping(target = "createdAt", expression = "java(currentTimestamp())")
    @Mapping(target = "updatedAt", expression = "java(currentTimestamp())")
    Rental toEntity(RentalDTO dto);

    @Mapping(target = "owner_id",   source     = "owner.id")
    @Mapping(target = "created_at", expression = "java(formatTimestamp(rental.getCreatedAt()))")
    @Mapping(target = "updated_at", expression = "java(formatTimestamp(rental.getUpdatedAt()))")
    RentalResponseDTO toResponseDTO(Rental rental);

    List<RentalResponseDTO> toResponseDTOList(List<Rental> rentals);
    
    default Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }
    
    default String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.toLocalDateTime().format(formatter);
    }
}