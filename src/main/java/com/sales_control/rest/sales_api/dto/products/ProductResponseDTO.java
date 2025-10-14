package com.sales_control.rest.sales_api.dto.products;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {

    private Long productId;
    private String productName;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String categoryName;
    private Long categoryId;
    private Long userId;
    private String imagePath;
}
