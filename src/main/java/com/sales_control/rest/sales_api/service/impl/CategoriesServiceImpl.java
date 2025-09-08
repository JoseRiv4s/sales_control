package com.sales_control.rest.sales_api.service.impl;

import com.sales_control.rest.sales_api.dto.CategoryRequestDTO;
import com.sales_control.rest.sales_api.dto.CategoryResponseDTO;
import com.sales_control.rest.sales_api.entities.CategoryEntity;
import com.sales_control.rest.sales_api.entities.UsersEntity;
import com.sales_control.rest.sales_api.exceptions.ResourceAlreadyExistsException;
import com.sales_control.rest.sales_api.exceptions.ResourceNotFoundException;
import com.sales_control.rest.sales_api.repository.CategoriesRepository;
import com.sales_control.rest.sales_api.repository.UsersRepository;
import com.sales_control.rest.sales_api.service.contract.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private static final Logger log = Logger.getLogger(CategoriesServiceImpl.class.getName());

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO, Long userId) {
        log.info("Inicio metodo createCategory en CategoriesServiceImpl");

        if (categoriesRepository.existsByCategoryName(categoryRequestDTO.getCategoryName())) {
            throw new ResourceAlreadyExistsException("La categoría ya existe");
        }

        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryRequestDTO.getCategoryName());
        categoryEntity.setUser(user);

        CategoryEntity savedCategory = categoriesRepository.save(categoryEntity);

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setCategoryId(savedCategory.getCategoryId());
        responseDTO.setCategoryName(savedCategory.getCategoryName());
        responseDTO.setCreatedAt(savedCategory.getCreatedAt());
        responseDTO.setUserId(savedCategory.getUser().getUserId());

        log.info("Termina metodo createCategory en CategoriesServiceImpl");
        return responseDTO;
    }

    @Override
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        log.info("Inicio metodo updateCategory");

        CategoryEntity categoryEntity = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        categoryEntity.setCategoryName(categoryRequestDTO.getCategoryName());

        CategoryEntity updatedCategory = categoriesRepository.save(categoryEntity);

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setCategoryId(updatedCategory.getCategoryId());
        responseDTO.setCategoryName(updatedCategory.getCategoryName());
        responseDTO.setUpdatedAt(updatedCategory.getUpdatedAt());
        responseDTO.setUserId(updatedCategory.getUser().getUserId());

        log.info("Termina metodo updateCategory");
        return responseDTO;
    }

    @Override
    public List<CategoryResponseDTO> findAllCategories() {
        return categoriesRepository.findAll()
                .stream()
                .map(category -> {
                    CategoryResponseDTO dto = new CategoryResponseDTO();
                    dto.setCategoryId(category.getCategoryId());
                    dto.setCategoryName(category.getCategoryName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        if (!categoriesRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Categoría no encontrada");
        }
        categoriesRepository.deleteById(categoryId);
    }
}