package com.shadcn.identity.mapper;

import org.mapstruct.Mapper;

import com.shadcn.identity.dto.response.BaseDTO;
import com.shadcn.identity.entity.BaseEntity;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    BaseDTO baseEntityToBaseDTO(BaseEntity baseEntity);
}
