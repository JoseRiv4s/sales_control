package com.sales_control.rest.sales_api.service.contract;

import com.sales_control.rest.sales_api.dto.products.ProductRequestDTO;
import com.sales_control.rest.sales_api.dto.products.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductsService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long categoryId, Long userId, MultipartFile imageFile );
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO, MultipartFile imageFile);
    ProductResponseDTO findProductById(Long productId);
    List<ProductResponseDTO> findAllProducts();
    void deleteProductById(Long productId);

}
