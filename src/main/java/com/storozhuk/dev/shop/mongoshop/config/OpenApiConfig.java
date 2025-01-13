package com.storozhuk.dev.shop.mongoshop.config;

import com.storozhuk.dev.shop.mongoshop.config.properties.AppProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {
  @Bean
  public OpenAPI openApi(AppProperties appProperties) {
    return new OpenAPI()
        .info(
            new Info()
                .title("Mongo Shop")
                .description("Mongo Shop API")
                .version(appProperties.version()));
  }
}
