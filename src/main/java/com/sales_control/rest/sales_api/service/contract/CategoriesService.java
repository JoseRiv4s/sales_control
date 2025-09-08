package com.sales_control.rest.sales_api.service.contract;

import com.sales_control.rest.sales_api.dto.CategoryRequestDTO;
import com.sales_control.rest.sales_api.dto.CategoryResponseDTO;
import java.util.List;

public interface CategoriesService {
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO, Long userId);
    CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO);
    List<CategoryResponseDTO> findAllCategories();
    void deleteCategoryById(Long categoryId);
}