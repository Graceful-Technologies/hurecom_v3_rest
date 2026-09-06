package com.gt.hurecom.mapper.master;

import com.gt.hurecom.dto.master.ClientLocationRequest;
import com.gt.hurecom.dto.master.ClientLocationResponse;
import com.gt.hurecom.entity.master.ClientLocation;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientLocationMapper {

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "city", ignore = true)
    ClientLocation convertToEntity(ClientLocationRequest request);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "city", ignore = true)
    void updateFromRequest(ClientLocationRequest request, @MappingTarget ClientLocation clientLocation);

    @InheritConfiguration(name = "toBase")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "state.name", target = "stateName")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(
            target = "displayName",
            expression = "java(clientLocation.getBranchName() + \" • \" + clientLocation.getCity().getName())"
    )
    ClientLocationResponse convertToResponse(ClientLocation clientLocation);

}
