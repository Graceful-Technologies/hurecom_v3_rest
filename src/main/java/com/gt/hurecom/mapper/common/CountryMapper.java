package com.gt.hurecom.mapper.common;

import com.gt.hurecom.dto.common.CountryResponse;
import com.gt.hurecom.entity.common.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryResponse convertToResponse(Country country);

}
