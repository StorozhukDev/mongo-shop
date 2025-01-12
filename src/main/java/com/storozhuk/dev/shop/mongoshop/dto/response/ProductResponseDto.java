package com.storozhuk.dev.shop.mongoshop.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public record ProductResponseDto(
    String id,
    String name,
    String category,
    String description,
    BigDecimal price,
    Map<String, Object> attributes) {}
