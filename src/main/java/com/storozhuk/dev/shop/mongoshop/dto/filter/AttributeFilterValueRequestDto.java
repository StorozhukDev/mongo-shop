package com.storozhuk.dev.shop.mongoshop.dto.filter;

public sealed interface AttributeFilterValueRequestDto
    permits NumericAttributeFilterValueRequestDto,
        StringAttributeFilterValueRequestDto,
        ExistsAttributeFilterValueRequestDto {}
