package com.storozhuk.dev.shop.mongoshop.service.impl;

import com.storozhuk.dev.shop.mongoshop.dto.filter.ProductFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.request.ProductRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.response.ProductResponseDto;
import com.storozhuk.dev.shop.mongoshop.mapper.ProductMapper;
import com.storozhuk.dev.shop.mongoshop.model.ProductEntity;
import com.storozhuk.dev.shop.mongoshop.repository.ProductRepository;
import com.storozhuk.dev.shop.mongoshop.service.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
    ProductEntity productEntity = productMapper.toEntity(productRequestDto);
    ProductEntity savedEntity = productRepository.save(productEntity);
    return productMapper.toResponseDto(savedEntity);
  }

  @Override
  public Page<ProductResponseDto> getProducts(ProductFilterRequestDto filter, Pageable pageable) {
    Page<ProductEntity> productEntities = productRepository.findByFilters(filter, pageable);
    return productEntities.map(productMapper::toResponseDto);
  }

  @Override
  public Optional<ProductResponseDto> getProductById(String id) {
    return productRepository.findById(id).map(productMapper::toResponseDto);
  }
}
