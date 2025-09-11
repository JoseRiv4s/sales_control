package com.sales_control.rest.sales_api.dto.products;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private String productName;
    private String description;
    private Integer quantity;
    private BigDecimal price;

    private Long categoryId;
    private String imagePath;
}
