package com.storozhuk.dev.shop.mongoshop.dto.filter;

public record StringAttributeFilterValueRequestDto(String searchText)
    implements AttributeFilterValueRequestDto {}
