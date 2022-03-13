package com.alkomis.shop.model;

import com.alkomis.shop.model.enums.TargetAudience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class represents entity of product in a shop.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    /**
     * Primary key. Autoincrement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Product name.
     */
    private String title;

    /**
     * Product price.
     */
    private BigDecimal price;

    /**
     * Product description.
     */
    private String description;

    /**
     * Brand of a product.
     */
    private String brand;

    /**
     * Flag for deleting products.
     */
    private boolean archive;

    /**
     * Target audience of this particular product.
     */
    @Enumerated(EnumType.STRING)
    private TargetAudience targetAudience;

    /**
     * Product properties (such as color, size, etc.).
     */
    @OneToMany(targetEntity = ProductProperties.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product")
    private List<ProductProperties> productProperties;

    /**
     * Category (type) of a product.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;
}