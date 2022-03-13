package com.alkomis.shop.controller;

import com.alkomis.shop.dto.CategoryDTO;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.model.Category;
import com.alkomis.shop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller, for all endpoints to interact with {@link Category} entity.
 */
@Tag(name = "Category", description = "Category API")
@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Provides list of all categories from database.
     *
     * @return list of all {@link CategoryDTO} from database.
     * @throws ElementNotFoundException when no category was not found.
     */
    @Operation(summary = "Get list of all categories from database")
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Provides {@link CategoryDTO} object, which contains products within specific category and category itself.
     *
     * @param categoryId id of {@link Category} entity.
     * @return object of {@link CategoryDTO} class with list of products within this category.
     */
    @Operation(summary = "Gets list of all product of category with provided id and category itself")
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getAllProductsOfCategory(@PathVariable("id") long categoryId) {
        CategoryDTO productsByCategory = categoryService.getAllProductsByCategoryId(categoryId);
        return new ResponseEntity<>(productsByCategory, HttpStatus.OK);
    }

}

