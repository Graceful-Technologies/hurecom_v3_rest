package com.gt.hurecom.mapper.common;

import com.gt.hurecom.dto.common.StateResponse;
import com.gt.hurecom.entity.common.State;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {

    StateResponse convertToResponse(State state);

}
