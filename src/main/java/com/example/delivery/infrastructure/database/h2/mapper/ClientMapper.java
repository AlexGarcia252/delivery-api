package com.example.delivery.infrastructure.database.h2.mapper;

import com.example.delivery.domain.model.Client;
import com.example.delivery.infrastructure.database.h2.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toDomain(ClientEntity clientEntity);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    ClientEntity toEntity(Client domain);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDomain(Client client, @MappingTarget ClientEntity entity);
}
