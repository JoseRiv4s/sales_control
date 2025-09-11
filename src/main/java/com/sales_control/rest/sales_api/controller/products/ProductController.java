package com.sales_control.rest.sales_api.controller.products;

import com.sales_control.rest.sales_api.dto.products.ProductRequestDTO;
import com.sales_control.rest.sales_api.dto.products.ProductResponseDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(value = "/createProduct", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestPart("product") ProductRequestDTO productRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + email));

        ProductResponseDTO response = productsService.createProduct(
                productRequestDTO,
                productRequestDTO.getCategoryId(),
                user.getUserId(),
                imageFile
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping(value = "/updateProduct/{productId}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") ProductRequestDTO productRequestDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        ProductResponseDTO response = productsService.updateProduct(productId, productRequestDTO, imageFile);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        ProductResponseDTO response = productsService.findProductById(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> response = productsService.findAllProducts();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productsService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}


