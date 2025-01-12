package com.storozhuk.dev.shop.mongoshop.controller;

import com.storozhuk.dev.shop.mongoshop.dto.request.ProductRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.response.ProductResponseDto;
import com.storozhuk.dev.shop.mongoshop.service.facade.ProductFacade;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductFacade productFacade;

  @PostMapping
  public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
    return productFacade.createProduct(productRequestDto);
  }

  @GetMapping
  public Page<ProductResponseDto> getProducts(
      @RequestParam Map<String, String> requestParams, Pageable pageable) {
    return productFacade.getFilteredProducts(requestParams, pageable);
  }

  @GetMapping("/{id}")
  public ProductResponseDto getProductById(@PathVariable String id) {
    return productFacade.getProduct(id);
  }
}
