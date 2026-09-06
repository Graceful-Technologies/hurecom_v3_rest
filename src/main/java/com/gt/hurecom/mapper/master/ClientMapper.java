package com.gt.hurecom.mapper.master;

import com.gt.hurecom.dto.admin.TeamResponse;
import com.gt.hurecom.dto.master.ClientRequest;
import com.gt.hurecom.dto.master.ClientResponse;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.master.Client;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client convertToEntity(ClientRequest request);

    void updateFromRequest(ClientRequest request, @MappingTarget Client client);

    ClientResponse convertToResponse(Client client);

    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "lastModifiedUser.name", target = "lastModifiedUserName")
    ClientResponse convertToResponseWithAudit(Client client);

}
