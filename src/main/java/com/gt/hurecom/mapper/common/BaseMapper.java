package com.gt.hurecom.mapper.common;

import com.gt.hurecom.dto.common.BaseResponse;
import com.gt.hurecom.entity.common.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    @Mapping(source = "createdUser.name", target = "createdUserName")
    @Mapping(source = "lastModifiedUser.name", target = "lastModifiedUserName")
    BaseResponse toBase(BaseEntity entity);
}
