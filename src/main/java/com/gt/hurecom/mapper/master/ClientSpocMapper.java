package com.gt.hurecom.mapper.master;

import com.gt.hurecom.dto.master.ClientSpocRequest;
import com.gt.hurecom.dto.master.ClientSpocResponse;
import com.gt.hurecom.entity.master.ClientSpoc;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientSpocMapper {

    @Mapping(target = "client", ignore = true)
    ClientSpoc convertToEntity(ClientSpocRequest request);

    @Mapping(target = "client", ignore = true)
    void updateFromRequest(ClientSpocRequest request, @MappingTarget ClientSpoc clientSpoc);

    @InheritConfiguration(name = "toBase")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    ClientSpocResponse convertToResponse(ClientSpoc clientSpoc);

}
