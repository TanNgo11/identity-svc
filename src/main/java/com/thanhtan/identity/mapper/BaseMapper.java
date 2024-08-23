package com.thanhtan.identity.mapper;

import org.mapstruct.Mapper;

import com.thanhtan.identity.dto.response.BaseDTO;
import com.thanhtan.identity.entity.BaseEntity;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    BaseDTO baseEntityToBaseDTO(BaseEntity baseEntity);
}
