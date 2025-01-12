package com.storozhuk.dev.shop.mongoshop.service.factory;

import com.storozhuk.dev.shop.mongoshop.dto.filter.AttributeFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.ExistsAttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.NumericAttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.StringAttributeFilterValueRequestDto;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class AttributeFilterFactory {

  public AttributeFilterRequestDto create(String name, String filterType, String value) {
    return switch (filterType) {
      case "value" -> parseValueFilter(name, value);
      case "min" -> parseMinFilter(name, value);
      case "max" -> parseMaxFilter(name, value);
      case "search" -> parseSearchFilter(name, value);
      case "exists" -> parseExistsFilter(name, value);
      default -> throw new IllegalArgumentException("Unsupported filter type: " + filterType);
    };
  }

  private AttributeFilterRequestDto parseValueFilter(String name, String value) {
    try {
      BigDecimal numericValue = new BigDecimal(value);
      return new AttributeFilterRequestDto(
          name, new NumericAttributeFilterValueRequestDto(numericValue, numericValue));
    } catch (NumberFormatException e) {
      return new AttributeFilterRequestDto(name, new StringAttributeFilterValueRequestDto(value));
    }
  }

  private AttributeFilterRequestDto parseMinFilter(String name, String value) {
    BigDecimal minValue = new BigDecimal(value);
    return new AttributeFilterRequestDto(
        name, new NumericAttributeFilterValueRequestDto(minValue, null));
  }

  private AttributeFilterRequestDto parseMaxFilter(String name, String value) {
    BigDecimal maxValue = new BigDecimal(value);
    return new AttributeFilterRequestDto(
        name, new NumericAttributeFilterValueRequestDto(null, maxValue));
  }

  private AttributeFilterRequestDto parseSearchFilter(String name, String value) {
    return new AttributeFilterRequestDto(name, new StringAttributeFilterValueRequestDto(value));
  }

  private AttributeFilterRequestDto parseExistsFilter(String name, String value) {
    return new AttributeFilterRequestDto(
        name, new ExistsAttributeFilterValueRequestDto(Boolean.parseBoolean(value)));
  }
}
