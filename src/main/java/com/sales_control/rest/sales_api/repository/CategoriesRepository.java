package com.sales_control.rest.sales_api.repository;

import com.sales_control.rest.sales_api.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByCategoryName(String categoryName);
}
