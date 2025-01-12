package com.storozhuk.dev.shop.mongoshop.dto.filter;

public record ExistsAttributeFilterValueRequestDto(boolean exists)
    implements AttributeFilterValueRequestDto {}
