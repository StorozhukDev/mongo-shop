package com.storozhuk.dev.shop.mongoshop.model;

import static org.springframework.data.mongodb.core.mapping.FieldType.DOUBLE;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "products")
public class ProductEntity {
  @Id private String id;
  private String name;
  private String category;
  private String description;
  private List<String> images;

  @Field(targetType = DOUBLE)
  private BigDecimal price;

  private Map<String, Object> attributes;
}
