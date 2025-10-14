package com.sales_control.rest.sales_api.repository;

import com.sales_control.rest.sales_api.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByProductName(String productName);
}
