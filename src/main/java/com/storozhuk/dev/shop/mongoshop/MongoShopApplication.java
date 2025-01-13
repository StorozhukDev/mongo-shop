package com.storozhuk.dev.shop.mongoshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.storozhuk.dev.shop.mongoshop.config.properties")
public class MongoShopApplication {

  public static void main(String[] args) {
    SpringApplication.run(MongoShopApplication.class, args);
  }
}
