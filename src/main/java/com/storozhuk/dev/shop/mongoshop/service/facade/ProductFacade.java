package com.storozhuk.dev.shop.mongoshop.service.facade;

import com.storozhuk.dev.shop.mongoshop.dto.request.ProductRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.response.ProductResponseDto;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductFacade {
  ProductResponseDto createProduct(ProductRequestDto productRequestDto);

  Page<ProductResponseDto> getFilteredProducts(
      Map<String, String> requestParams, Pageable pageable);

  ProductResponseDto getProduct(String id);
}
