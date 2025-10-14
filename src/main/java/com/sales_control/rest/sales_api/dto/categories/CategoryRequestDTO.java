package com.sales_control.rest.sales_api.dto.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String categoryName;
}
