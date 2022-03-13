package com.alkomis.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Locale;

/**
 * Class represents DTO for {@link com.alkomis.shop.model.Category} entity of the shop.
 */
@Data
public class CategoryDTO {

    /**
     * Category id.
     */
    private long id;

    /**
     * Title of category.
     */
    @NotBlank(message = "Product title can't be empty.")
    @Size(min = 3, max = 255, message = "Product category must have at least 3 characters.")
    private String title;

    /**
     * Setter for title field with converter to lowercase.
     *
     * @param title title of a category in original case.
     */
    public void setTitle(String title) {
        this.title = title.toLowerCase(Locale.ROOT);
    }
}
