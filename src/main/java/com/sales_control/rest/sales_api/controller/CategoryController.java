package com.sales_control.rest.sales_api.controller;

import com.sales_control.rest.sales_api.dto.CategoryRequestDTO;
import com.sales_control.rest.sales_api.dto.CategoryResponseDTO;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.CategoriesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private static Logger log = Logger.getLogger(String.valueOf(CategoryController.class));

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.info("Inicio metodo createCategory en CategoryController");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        UsersEntity user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con username: " + email));

        CategoryResponseDTO createdCategory = categoriesService.createCategory(categoryRequestDTO, user.getUserId());

        log.info("Termina metodo createCategory en CategoryController");
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory (@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.info("Inicio metodo updateCategory en CategoryController");
        CategoryResponseDTO updatedCategory = categoriesService.updateCategory(categoryId, categoryRequestDTO);
        log.info("Termina metodo updateCategory en CategoryController");
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategories() {
        log.info("Inicio metodo findAllCategories en CategoryController");
        List<CategoryResponseDTO> categories = categoriesService.findAllCategories();
        log.info("Termina metodo findAllCategories en CategoryController");
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        log.info("Inicio metodo deleteCategory en CategoryController");
        categoriesService.deleteCategoryById(categoryId);
        log.info("Termina metodo deleteCategory en CategoryController");
        return ResponseEntity.noContent().build();
    }
}