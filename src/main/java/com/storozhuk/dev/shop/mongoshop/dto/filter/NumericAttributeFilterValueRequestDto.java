package com.storozhuk.dev.shop.mongoshop.dto.filter;

import java.math.BigDecimal;

public record NumericAttributeFilterValueRequestDto(BigDecimal min, BigDecimal max)
    implements AttributeFilterValueRequestDto {}
