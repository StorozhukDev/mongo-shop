# Product Management API

## Overview
The Product Management API is designed to facilitate the management and querying of products, supporting dynamic filtering and sorting capabilities. This API allows users to:

- Create a new product.
- Retrieve a list of products with advanced filtering options.
- Fetch detailed information about a specific product by its ID.

---

## Endpoints

### 1. Create a Product
**POST /api/v1/products**

Creates a new product.

#### Request Body
- **ProductRequestDto**: JSON payload containing product details.

#### Response
- **ProductResponseDto**: JSON payload with the created product's details.

---

### 2. Retrieve Products with Filtering
**GET /api/v1/products**

Fetches a paginated list of products, supporting dynamic filters.

#### Query Parameters
- **category** (optional): Filters products by category.
- **search** (optional): Performs a full-text search on product names and descriptions.
- **min_price** (optional): Filters products with a price greater than or equal to this value.
- **max_price** (optional): Filters products with a price less than or equal to this value.
- **attr_value_[attributeName]** (optional): Filters products where the specified attribute equals the given value.
- **attr_min_[attributeName]** (optional): Filters products where the specified attribute is greater than or equal to the given minimum value.
- **attr_max_[attributeName]** (optional): Filters products where the specified attribute is less than or equal to the given maximum value.
- **attr_search_[attributeName]** (optional): Filters products by performing a search on the specified attribute.
- **attr_exists_[attributeName]** (optional): Filters products where the specified attribute exists or not (boolean).
- **page** (optional): Page number for pagination (default is 0).
- **size** (optional): Page size for pagination (default is 20).
- **sort** (optional): Sorting criteria (e.g., `name,asc` or `price,desc`).

#### Response
- **Page<ProductResponseDto>**: A paginated list of product details.

---

### 3. Retrieve Product by ID
**GET /api/v1/products/{id}**

Fetches the details of a specific product by its ID.

#### Path Variables
- **id**: The unique identifier of the product.

#### Response
- **ProductResponseDto**: JSON payload with the product's details.

---

## Filtering Guide

### Standard Filters
| Parameter       | Description                                     |
|-----------------|-------------------------------------------------|
| `category`      | Filter by product category.                     |
| `search`        | Full-text search on product names/descriptions. |
| `min_price`     | Filter by minimum product price.                |
| `max_price`     | Filter by maximum product price.                |

### Attribute Filters
| Parameter                          | Description                                                            |
|------------------------------------|------------------------------------------------------------------------|
| `attr_value_[attributeName]`       | Matches exact value of the given attribute.                            |
| `attr_min_[attributeName]`         | Matches values greater than or equal to the specified minimum.         |
| `attr_max_[attributeName]`         | Matches values less than or equal to the specified maximum.            |
| `attr_search_[attributeName]`      | Matches products containing the search term within the attribute.      |
| `attr_exists_[attributeName]`      | Filters products based on the existence of the attribute (true/false). |

### Example Usage
#### URL Example:
```
GET /api/v1/products?category=electronics&min_price=100&attr_value_color=blue&page=0&size=10&sort=name,asc
```

---

## Error Handling
- **400 Bad Request**: Invalid query parameter or request payload.
- **404 Not Found**: Resource not found (e.g., product ID does not exist).
- **500 Internal Server Error**: Unexpected error occurred.

---

