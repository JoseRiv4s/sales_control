package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.dto.products.ProductRequestDTO;
import com.sales_control.rest.sales_api.dto.products.ProductResponseDTO;
import com.sales_control.rest.sales_api.entities.CategoryEntity;
import com.sales_control.rest.sales_api.entities.ImageEntity;
import com.sales_control.rest.sales_api.entities.ProductEntity;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.ResourceAlreadyExistsException;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.repository.CategoriesRepository;
import com.sales_control.rest.sales_api.repository.ImagesRepository;
import com.sales_control.rest.sales_api.repository.ProductsRepository;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {

    private static final Logger log = Logger.getLogger(ProductsServiceImpl.class.getName());

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto, Long categoryId, Long userId, MultipartFile imageFile) {
        log.info("Inicio metodo createProduct en ProductsServiceImpl");

        if (productsRepository.existsByProductName(dto.getProductName())) {
            throw new ResourceAlreadyExistsException("El producto ya existe");
        }

        CategoryEntity category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + categoryId));

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

        // Construir producto
        ProductEntity product = new ProductEntity();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setUser(user);

        // Si viene imagen, construirla y asignarla ANTES de persistir
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get("uploads/products/" + fileName);

            try {
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen", e);
            }

            ImageEntity image = new ImageEntity();
            image.setImageName(fileName);
            image.setImagePath(imagePath.toString());
            image.setUploadedAt(LocalDateTime.now());

            // Relación (owner = product.image)
            product.setImage(image);
            image.setProduct(product);
        }

        // Guardar SOLO UNA VEZ
        ProductEntity savedProduct = productsRepository.save(product);

        // Respuesta
        ProductResponseDTO response = new ProductResponseDTO();
        response.setProductId(savedProduct.getProductId());
        response.setProductName(savedProduct.getProductName());
        response.setDescription(savedProduct.getDescription());
        response.setQuantity(savedProduct.getQuantity());
        response.setPrice(savedProduct.getPrice());
        response.setCreatedAt(savedProduct.getCreatedAt());
        response.setCategoryId(savedProduct.getCategory().getCategoryId());
        response.setUserId(savedProduct.getUser().getUserId());

        if (savedProduct.getImage() != null) {
            response.setImagePath(savedProduct.getImage().getImagePath());
        }

        log.info("Termina metodo createProduct en ProductsServiceImpl");
        return response;
    }


    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO dto, MultipartFile imageFile) {
        log.info("Inicia metodo updateProduct en ProductsServiceImpl");

        // 1. Buscar producto
        ProductEntity product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        // 2. Actualizar datos básicos
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());

        // 3. Si viene imagen nueva, guardarla y reemplazar la anterior
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get("uploads/products/" + fileName);

            try {
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen", e);
            }

            ImageEntity image = product.getImage();
            if (image == null) {
                image = new ImageEntity();
                image.setProduct(product);
            }

            image.setImageName(fileName);
            image.setImagePath(imagePath.toString());
            image.setUploadedAt(LocalDateTime.now());

            product.setImage(image);
        }

        // 4. Guardar cambios
        ProductEntity updatedProduct = productsRepository.save(product);

        // 5. Construir respuesta
        ProductResponseDTO response = new ProductResponseDTO();
        response.setProductId(updatedProduct.getProductId());
        response.setProductName(updatedProduct.getProductName());
        response.setDescription(updatedProduct.getDescription());
        response.setQuantity(updatedProduct.getQuantity());
        response.setPrice(updatedProduct.getPrice());
        response.setUpdatedAt(updatedProduct.getUpdatedAt());
        response.setCategoryId(updatedProduct.getCategory().getCategoryId());
        response.setUserId(updatedProduct.getUser().getUserId());

        if (updatedProduct.getImage() != null) {
            response.setImagePath(updatedProduct.getImage().getImagePath());
        }

        log.info("Termina metodo updateProduct en ProductsServiceImpl");
        return response;
    }

    @Override
    public ProductResponseDTO findProductById(Long productId) {
        log.info("Inicia metodo findProductById en ProductsServiceImpl");

        ProductEntity product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setCategoryId(product.getCategory().getCategoryId());

        dto.setUserId(product.getUser().getUserId());

        // AGREGAR LA IMAGEN
        if (product.getImage() != null) {
            String imagePath = product.getImage().getImagePath();
            // Limpiar la ruta para URLs web
            dto.setImagePath(imagePath.replace("\\", "/"));
        }

        log.info("Termina metodo findProductById en ProductsServiceImpl");
        return dto;
    }

    @Override
    public List<ProductResponseDTO> findAllProducts() {
        log.info("Inicia metodo findAllProducts en ProductsServiceImpl");
        log.info("Termina metodo findAllProducts en ProductsServiceImpl");
        return productsRepository.findAll()
                .stream()
                .map(product -> {
                    ProductResponseDTO productDTO = new ProductResponseDTO();
                    productDTO.setProductId(product.getProductId());
                    productDTO.setProductName(product.getProductName());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setQuantity(product.getQuantity());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setCreatedAt(product.getCreatedAt());
                    productDTO.setCategoryId(product.getCategory().getCategoryId());
                    productDTO.setUserId(product.getUser().getUserId());

                    // AGREGAR LA IMAGEN
                    if (product.getImage() != null) {
                        String imagePath = product.getImage().getImagePath();
                        // Limpiar la ruta para URLs web
                        productDTO.setImagePath(imagePath.replace("\\", "/"));
                    }

                    return productDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public void deleteProductById(Long productId) {
        log.info("Inicia metodo deleteProductById en ProductsServiceImpl");
        if (!productsRepository.existsById(productId)){
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        productsRepository.deleteById(productId);
        log.info("Termina metodo deleteProductById en ProductsServiceImpl");
    }

}

