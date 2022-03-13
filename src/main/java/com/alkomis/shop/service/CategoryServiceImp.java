package com.alkomis.shop.service;

import com.alkomis.shop.dto.CategoryDTO;
import com.alkomis.shop.dto.CategoryWithProductsDTO;
import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.mappers.ProductMapper;
import com.alkomis.shop.model.Category;
import com.alkomis.shop.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * Class contains implementation of {@link CategoryService} interface.
 */
@Slf4j
@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    @Lazy
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public List<CategoryDTO> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        List<CategoryDTO> allCategoriesDTO = allCategories
                .stream()
                .map(category -> productMapper.categoryToCategoryDto(category)).toList();

        if (allCategories.isEmpty()) {
            log.error("There is no categories in database.");
            throw new ElementNotFoundException("Can't find any categories.");
        }

        log.info("Found categories in database: " + allCategories);
        return allCategoriesDTO;
    }

    @Override
    @Transactional
    public CategoryDTO getAllProductsByCategoryId(long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            log.error("Can't find category with id: " + id);
            throw new ElementNotFoundException("Can't find category with id: " + id);
        }

        log.info("Found category with id " + id + ": " + category.get());
        CategoryDTO categoryDTO = productMapper.categoryToCategoryDto(category.get());

        List<ProductDTO> listOfProductsInCategory = productService.findProductsByCategoryId(id);
        log.info("For category with id " + id + " was found list of products: " + listOfProductsInCategory);

        // Create object to return.
        CategoryWithProductsDTO result = new CategoryWithProductsDTO();
        result.setId(categoryDTO.getId());
        result.setTitle(categoryDTO.getTitle());
        result.getProductList().addAll(listOfProductsInCategory);

        return result;
    }

    @Override
    @Transactional
    public Category findTopCategoryByTitle(String title) {
        Optional<Category> category = categoryRepository.findTopCategoryByTitle(title);

        if (category.isEmpty()) {
            log.error("Can't find category with title: " + title);
            return null;
        } else {
            log.info("Found category in database: " + category.get());
        }

        return category.get();
    }
}
