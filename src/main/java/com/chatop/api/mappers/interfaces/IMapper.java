package com.chatop.api.mappers.interfaces;

import java.util.List;
public interface IMapper<Entity, RequestDTO, ResponseDTO> {
    // TODO: @Mapstruct
    Entity            toEntity(RequestDTO dto);
    ResponseDTO       toResponseDTO(Entity entity);
    List<ResponseDTO> toResponseDTOList(List<Entity> entities);
}