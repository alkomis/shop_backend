package com.alkomis.shop.dto;

import com.alkomis.shop.model.enums.TargetAudience;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents DTO for {@link com.alkomis.shop.model.Product} entity of the shop.
 */
@Data
public class ProductDTO {

    /**
     * Product id.
     */
    private long id;

    /**
     * Title of product.
     */
    @NotBlank(message = "Product title can't be empty.")
    @Size(min = 3, max = 255, message = "Product title must have at least 3 characters.")
    private String title;

    /**
     * Product price.
     */
    @Positive(message = "Product price must be greater than 0.")
    private BigDecimal price;

    /**
     * Product description.
     */
    @NotBlank(message = "Product description can't be empty.")
    @Size(min = 5, max = 2048, message = "Product description must have at least 3 characters.")
    private String description;

    /**
     * Brand of a product.
     */
    @NotBlank(message = "Product brand can't be empty.")
    @Size(min = 2, message = "Product brand name must have at least 2 characters.")
    private String brand;

    /**
     * Target audience of this particular product.
     */
    private TargetAudience targetAudience;

    /**
     * List of different properties for this particular product. Size, color, etc.
     */
    @NotEmpty(message = "Product properties can't be empty.")
    @Valid
    private List<ProductPropertiesDTO> productProperties = new ArrayList<>();

    /**
     * Category of this product.
     */
    @NotNull(message = "Product category can't be null")
    @Valid
    private CategoryDTO category;
}
