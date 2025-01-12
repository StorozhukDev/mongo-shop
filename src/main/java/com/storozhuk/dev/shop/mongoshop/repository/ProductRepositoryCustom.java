package com.storozhuk.dev.shop.mongoshop.repository;

import com.storozhuk.dev.shop.mongoshop.dto.filter.ProductFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

  Page<ProductEntity> findByFilters(ProductFilterRequestDto filter, Pageable pageable);
}
