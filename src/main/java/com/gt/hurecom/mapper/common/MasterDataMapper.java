package com.gt.hurecom.mapper.common;

import com.gt.hurecom.dto.common.MasterDataResponse;
import com.gt.hurecom.entity.common.MasterData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterDataMapper {

    MasterDataResponse convertToResponse(MasterData masterData);
}
