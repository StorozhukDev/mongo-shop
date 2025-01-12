package com.storozhuk.dev.shop.mongoshop.service;

import com.storozhuk.dev.shop.mongoshop.dto.filter.ProductFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.request.ProductRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.response.ProductResponseDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductResponseDto createProduct(ProductRequestDto productRequestDto);

  Page<ProductResponseDto> getProducts(ProductFilterRequestDto filter, Pageable pageable);

  Optional<ProductResponseDto> getProductById(String id);
}
