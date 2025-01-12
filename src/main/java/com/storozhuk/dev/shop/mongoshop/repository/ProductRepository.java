package com.storozhuk.dev.shop.mongoshop.repository;

import com.storozhuk.dev.shop.mongoshop.model.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
    extends MongoRepository<ProductEntity, String>, ProductRepositoryCustom {}
