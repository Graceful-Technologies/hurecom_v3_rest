package com.gt.hurecom.mapper.common;

import com.gt.hurecom.dto.common.CityResponse;
import com.gt.hurecom.entity.common.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityResponse convertToResponse(City city);

}
