package com.alkomis.shop.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents DTO for {@link com.alkomis.shop.model.Category} entity with list of {@link ProductDTO} objects of category.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryWithProductsDTO extends CategoryDTO {

    /**
     * List of products of this category.
     */
    private List<ProductDTO> productList = new ArrayList<>();
}

