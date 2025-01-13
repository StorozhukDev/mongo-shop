package com.storozhuk.dev.shop.mongoshop.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ProductRequestDto(
    String name,
    String category,
    String description,
    List<String> images,
    BigDecimal price,
    Map<String, Object> attributes) {}
