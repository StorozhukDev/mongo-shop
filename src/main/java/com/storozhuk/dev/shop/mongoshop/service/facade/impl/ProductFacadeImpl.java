package com.storozhuk.dev.shop.mongoshop.service.facade.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.storozhuk.dev.shop.mongoshop.dto.filter.AttributeFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.NumericAttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.ProductFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.request.ProductRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.response.ProductResponseDto;
import com.storozhuk.dev.shop.mongoshop.exception.ResourceNotFoundException;
import com.storozhuk.dev.shop.mongoshop.service.ProductService;
import com.storozhuk.dev.shop.mongoshop.service.facade.ProductFacade;
import com.storozhuk.dev.shop.mongoshop.service.factory.AttributeFilterFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {

  private final ProductService productService;
  private final AttributeFilterFactory attributeFilterFactory;

  @Override
  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
    return productService.createProduct(productRequestDto);
  }

  public Page<ProductResponseDto> getFilteredProducts(
      Map<String, String> requestParams, Pageable pageable) {
    // Extract standard parameters
    String category = requestParams.remove("category");
    String search = requestParams.remove("search");
    BigDecimal minPrice = parseBigDecimal(requestParams.remove("min_price"));
    BigDecimal maxPrice = parseBigDecimal(requestParams.remove("max_price"));

    // Remove pagination parameters
    requestParams.remove("page");
    requestParams.remove("size");
    requestParams.remove("sort");

    // Build attribute filters
    List<AttributeFilterRequestDto> attributeFilters = constructAttributeFilters(requestParams);

    ProductFilterRequestDto filter =
        new ProductFilterRequestDto(category, search, minPrice, maxPrice, attributeFilters);
    return productService.getProducts(filter, pageable);
  }

  @Override
  public ProductResponseDto getProduct(String id) {
    return productService
        .getProductById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
  }

  private List<AttributeFilterRequestDto> constructAttributeFilters(Map<String, String> params) {
    Map<String, AttributeFilterRequestDto> attributeFiltersMap = new HashMap<>();
    Pattern pattern = Pattern.compile("attr_(value|min|max|search|exists)_(.+)");

    for (Map.Entry<String, String> entry : params.entrySet()) {
      Matcher matcher = pattern.matcher(entry.getKey());
      if (matcher.matches()) {
        String filterType = matcher.group(1);
        String attributeName = matcher.group(2);

        AttributeFilterRequestDto existingFilter = attributeFiltersMap.get(attributeName);
        AttributeFilterRequestDto newFilter =
            attributeFilterFactory.create(attributeName, filterType, entry.getValue());

        attributeFiltersMap.put(attributeName, mergeFilters(existingFilter, newFilter));
      }
    }

    return new ArrayList<>(attributeFiltersMap.values());
  }

  private AttributeFilterRequestDto mergeFilters(
      AttributeFilterRequestDto existingFilter, AttributeFilterRequestDto newFilter) {
    if (isNull(existingFilter)) {
      return newFilter;
    }

    if (existingFilter.value() instanceof NumericAttributeFilterValueRequestDto existingValue
        && newFilter.value() instanceof NumericAttributeFilterValueRequestDto newValue) {
      BigDecimal min = nonNull(newValue.min()) ? newValue.min() : existingValue.min();
      BigDecimal max = nonNull(newValue.max()) ? newValue.max() : existingValue.max();
      return new AttributeFilterRequestDto(
          existingFilter.name(), new NumericAttributeFilterValueRequestDto(min, max));
    }

    // Otherwise, return the existing filter (not overwrite)
    return existingFilter;
  }

  private BigDecimal parseBigDecimal(String value) {
    if (isNull(value)) {
      return null;
    }
    try {
      return new BigDecimal(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number format: " + value, e);
    }
  }
}
