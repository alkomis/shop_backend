package com.alkomis.shop.service;

import com.alkomis.shop.dto.CategoryDTO;
import com.alkomis.shop.dto.ProductDTO;
import com.alkomis.shop.exceptions.ElementNotFoundException;
import com.alkomis.shop.model.Category;

import java.util.List;

/**
 * Interface of a {@link Category} class service.
 */
public interface CategoryService {

    /**
     * Returns list of all categories from database.
     *
     * @return List of a {@link CategoryDTO} objects.
     * @throws ElementNotFoundException if List is empty.
     */
    List<CategoryDTO> getAllCategories();

    /**
     * Returns object of {@link CategoryDTO} which contains category and list of {@link ProductDTO} of this category.
     *
     * @param id of the {@link Category} entity.
     * @return object of {@link CategoryDTO} class.
     * @throws ElementNotFoundException if there is no such category exist.
     */
    CategoryDTO getAllProductsByCategoryId(long id);

    /**
     * Returns first {@link Category} which title equal to provided String.
     *
     * @param title of {@link Category}.
     * @return first occurrence of a {@link Category} with this parameter in database.
     * @throws ElementNotFoundException if category was not found.
     */
    Category findTopCategoryByTitle(String title);
}
