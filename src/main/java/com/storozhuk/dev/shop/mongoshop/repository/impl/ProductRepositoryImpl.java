package com.storozhuk.dev.shop.mongoshop.repository.impl;

import static java.util.Objects.nonNull;

import com.storozhuk.dev.shop.mongoshop.dto.filter.AttributeFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.AttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.ExistsAttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.NumericAttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.ProductFilterRequestDto;
import com.storozhuk.dev.shop.mongoshop.dto.filter.StringAttributeFilterValueRequestDto;
import com.storozhuk.dev.shop.mongoshop.model.ProductEntity;
import com.storozhuk.dev.shop.mongoshop.repository.ProductRepositoryCustom;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public Page<ProductEntity> findByFilters(ProductFilterRequestDto filter, Pageable pageable) {
    List<Criteria> criteriaList = new ArrayList<>();

    // Add filters to the criteria list
    addCategoryFilter(criteriaList, filter.category());
    addSearchFilter(criteriaList, filter.search());
    addPriceFilter(criteriaList, filter.minPrice(), filter.maxPrice());
    addAttributeFilters(criteriaList, filter.attributes());

    Criteria criteriaDefinition =
        criteriaList.isEmpty()
            ? new Criteria()
            : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
    Query query = new Query(criteriaDefinition);

    long totalItems = mongoTemplate.count(query, ProductEntity.class);
    query.with(pageable);

    log.debug("Constructed query: {}", query);
    List<ProductEntity> products = mongoTemplate.find(query, ProductEntity.class);

    return new PageImpl<>(products, pageable, totalItems);
  }

  private void addCategoryFilter(List<Criteria> criteriaList, String category) {
    if (nonNull(category)) {
      criteriaList.add(Criteria.where("category").is(category));
    }
  }

  private void addSearchFilter(List<Criteria> criteriaList, String search) {
    if (nonNull(search)) {
      String regex = ".*" + Pattern.quote(search) + ".*";
      criteriaList.add(
          new Criteria()
              .orOperator(
                  Criteria.where("description").regex(regex, "i"),
                  Criteria.where("name").regex(regex, "i")));
    }
  }

  private void addPriceFilter(
      List<Criteria> criteriaList, BigDecimal minPrice, BigDecimal maxPrice) {
    if (nonNull(minPrice) || nonNull(maxPrice)) {
      Criteria priceCriteria = Criteria.where("price");
      if (nonNull(minPrice)) {
        priceCriteria.gte(minPrice.doubleValue());
      }
      if (nonNull(maxPrice)) {
        priceCriteria.lte(maxPrice.doubleValue());
      }
      criteriaList.add(priceCriteria);
    }
  }

  private void addAttributeFilters(
      List<Criteria> criteriaList, List<AttributeFilterRequestDto> attributeFilters) {
    if (CollectionUtils.isEmpty(attributeFilters)) {
      return;
    }

    List<Criteria> attributeCriteriaList = new ArrayList<>();
    for (AttributeFilterRequestDto filter : attributeFilters) {
      String attributeName = "attributes." + filter.name();
      AttributeFilterValueRequestDto value = filter.value();

      if (value instanceof NumericAttributeFilterValueRequestDto numericValue) {
        Criteria numericCriteria = Criteria.where(attributeName);
        if (nonNull(numericValue.min())) {
          numericCriteria.gte(numericValue.min().doubleValue());
        }
        if (nonNull(numericValue.max())) {
          numericCriteria.lte(numericValue.max().doubleValue());
        }
        attributeCriteriaList.add(numericCriteria);
      } else if (value instanceof StringAttributeFilterValueRequestDto stringValue) {
        String regex = ".*" + Pattern.quote(stringValue.searchText()) + ".*";
        attributeCriteriaList.add(Criteria.where(attributeName).regex(regex, "i"));
      } else if (value instanceof ExistsAttributeFilterValueRequestDto existsValue) {
        attributeCriteriaList.add(Criteria.where(attributeName).exists(existsValue.exists()));
      }
    }

    if (!attributeCriteriaList.isEmpty()) {
      criteriaList.add(new Criteria().andOperator(attributeCriteriaList.toArray(new Criteria[0])));
    }
  }
}
