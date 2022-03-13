package com.alkomis.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class represents entity of {@link Product} properties.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_properties")
public class ProductProperties {

    /**
     * Primary key. Autoincrement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Size of a product.
     */
    private double size;

    /**
     * Color of a product.
     */
    private String color;

    /**
     * Link to image of product
     */
    private String imageLink;

    /**
     * Field represents how much of a product with this particular properties shop have in stock.
     */
    private int stock;

}
