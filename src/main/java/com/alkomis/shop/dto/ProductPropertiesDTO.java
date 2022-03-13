package com.alkomis.shop.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * Class represents DTO for {@link com.alkomis.shop.model.ProductProperties} entity of the shop.
 */
@Data
public class ProductPropertiesDTO {

    /**
     * Size of a product.
     */
    @Min(value = 33, message = "Size should be between 33 and 48.")
    @Max(value = 48, message = "Size should be between 33 and 48.")
    private double size;

    /**
     * Color of a product.
     */
    @NotBlank(message = "Color shouldn't be empty.")
    private String color;

    /**
     * Link to image of product
     */
    @NotBlank(message = "Provide link to image.")
    private String imageLink;

    /**
     * Field represents how much of a product with this particular properties shop have in stock.
     */
    @PositiveOrZero(message = "Minimal value of stock should be 0 or greater.")
    private int stock;
}
