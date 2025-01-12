package com.storozhuk.dev.shop.mongoshop.dto.filter;

import java.math.BigDecimal;
import java.util.List;

public record ProductFilterRequestDto(
    String category,
    String search,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    List<AttributeFilterRequestDto> attributes) {}
