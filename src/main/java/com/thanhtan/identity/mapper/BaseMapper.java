package com.thanhtan.identity.mapper;

import com.thanhtan.identity.dto.response.BaseDTO;
import com.thanhtan.identity.entity.BaseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    BaseDTO baseEntityToBaseDTO(BaseEntity baseEntity);
}