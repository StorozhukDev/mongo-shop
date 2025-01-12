package com.storozhuk.dev.shop.mongoshop.mapper;

import com.storozhuk.dev.shop.mongoshop.dto.request.ProductRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.response.ProductResponseDto;
import com.storozhuk.dev.shop.mongoshop.model.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductEntity toEntity(ProductRequestDto dto);

  ProductResponseDto toResponseDto(ProductEntity entity);
}
